#!/usr/bin/env python
# -*- coding: utf-8 -*-

import collections
import logging

from zapv2 import ZAPv2

from .. import ZapClient
from ..configuration import ZapConfiguration
from .zap_context_authentication import ZapConfigureContextAuthentication

# set up logging to file - see previous section for more details
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s %(name)-12s %(levelname)-8s: %(message)s',
    datefmt='%Y-%m-%d %H:%M')

logging = logging.getLogger('ZapConfigureContext')

class ZapConfigureContext(ZapClient):
    """This class configures the context in running ZAP instance, based on a given ZAP Configuration.
    
    Based on this opensource ZAP Python example:
    - https://github.com/zaproxy/zap-api-python/blob/9bab9bf1862df389a32aab15ea4a910551ba5bfc/src/examples/zap_example_api_script.py
    
    """

    def __init__(self, zap: ZAPv2, config: ZapConfiguration):
        """Initial constructor used for this class
        
        Parameters
        ----------
        zap : ZAPv2
            The running ZAP instance to configure.
        config : ZapConfiguration
            The configuration object containing all ZAP configs (based on the class ZapConfiguration).
        """
        
        super().__init__(zap, config) 
        
    def configure_contexts(self):
        """ Configures the ZAP instance with the given list of contexts."""

        if self.get_config.has_configurations:

            contexts = self.get_config.get_contexts.get_configurations

            logging.debug('Configuring the List of #%s context(s) with: %s', len(contexts), contexts)

            # Remove all existing ZAP contexts 
            logging.info("Existing Contexts will be removed: %s", self.get_zap.context.context_list)
            for remove_context in self.get_zap.context.context_list:
                self.get_zap.context.remove_context(contextname=remove_context)

            # Add all new ZAP contexts
            for context in contexts:
                self._configure_context(context)
                
        else:
            logging.warning("No valid ZAP configuration object found: %s! It seems there is something important missing.", self.get_config)
    
    def _configure_context(self, context: collections.OrderedDict):
        """ Configures the ZAP instance with the context.
        
        Parameters
        ----------
        context : collections.OrderedDict
            The zap configuration object containing a single context configuration (based on the class ZapConfiguration).
        """
        
        context_name = context["name"]
        logging.debug('Configuring a new ZAP Context with name: ' + context_name)
        context_id = self.get_zap.context.new_context(context_name)
        context["id"] = context_id

        if self._is_not_empty("includePaths", context):
            self._configure_context_include(context)
        
        if self._is_not_empty("excludePaths", context):
            self._configure_context_exclude(context)
        
        if self._is_not_empty("authentication", context) and self._is_not_empty_string("type", context["authentication"]):
            configure_authenication = ZapConfigureContextAuthentication(zap=self.get_zap, config=self.get_config)
            configure_authenication.configure_context_authentication(context, context_id)
        
        if self._is_not_empty("users", context) and self._is_not_empty_string("type", context["authentication"]):
            self._configure_context_create_users(users=context["users"], auth_type=context["authentication"]["type"], context_id=context_id)
        
        if self._is_not_empty("session", context) and self._is_not_empty_string("type", context["session"]):
            self._configure_context_session_management(sessions_config=context["session"], context_id=context_id)
        
        if self._is_not_empty("technologies", context):
            # TODO: Open a new ZAP GH Issue: Why (or) is this difference (context_id vs. context_name) here really necessary?
            self._configure_context_technologies(context["technologies"], context_name)

    def _configure_context_include(self, context: collections.OrderedDict):
        """Protected method to configure the ZAP 'Context / Include Settings' based on a given ZAP config.
        
        Parameters
        ----------
        contexts : collections.OrderedDict
            The zap configuration object containing the ZAP include configuration (based on the class ZapConfiguration).
        """

        if "includePaths" in context:
            for regex in context["includePaths"]:
                logging.debug("Including regex '%s' from context", regex)
                self.get_zap.context.include_in_context(contextname=context["name"], regex=regex)

    def _configure_context_exclude(self, context: collections.OrderedDict):
        """Protected method to configure the ZAP 'Context / Exclude Settings' based on a given ZAP config.
        
        Parameters
        ----------
        contexts : collections.OrderedDict
            The current zap configuration object containing the ZAP exclude configuration (based on the class ZapConfiguration).
        """

        if "excludePaths" in context:
            for regex in context["excludePaths"]:
                logging.debug("Excluding regex '%s' from context", regex)
                self.get_zap.context.exclude_from_context(contextname=context["name"], regex=regex)

    def _configure_context_create_users(self, users: collections.OrderedDict, auth_type: str, context_id: int):
        """Protected method to configure the ZAP 'Context / Users Settings' based on a given ZAP config.
        
        Parameters
        ----------
        users : collections.OrderedDict
            The current users (list) configuration object containing the ZAP users configuration (based on the class ZapConfiguration).
        auth_type: str
            The configured authentiation type (e.g.: "basic-auth", "form-based", "json-based", "script-based").
        context_id : int
            The zap context id tot configure the ZAP authentication for (based on the class ZapConfiguration).
        """

        # Remove all existing ZAP Users for given context
        logging.info("Existing Users will be removed before adding new ones.")
        for user_id in self.get_zap.users.users_list(contextid=context_id):
            self.get_zap.users.remove_user(contextid=context_id, userid=user_id)

        # Add all new ZAP Users to given context
        for user in users:
            logging.debug("Adding ZAP User '%s', to context(%s)", user, context_id)
            user_name = user['username']
            user_password = user['password']
            
            user_id = self.get_zap.users.new_user(contextid=context_id, name=user_name)
            logging.debug("Created ZAP User(%s), for context(%s)", user_id, context_id)
            user['id'] = user_id
            
            self.get_zap.users.set_user_name(
                contextid=context_id, 
                userid=user_id, 
                name=user_name)

            # TODO: Open a new issue at ZAP GitHub: Why (or) is this difference (camelCase vs. pascalCase) here really necessary?
            if auth_type == "script-based":
                self.get_zap.users.set_authentication_credentials(
                    contextid=context_id,
                    userid=user_id,
                    authcredentialsconfigparams='Username=' + user_name + '&Password=' + user_password)
                self.get_zap.users.set_user_enabled(contextid=context_id, userid=user_id, enabled=True)
            else:
                self.get_zap.users.set_authentication_credentials(
                    contextid=context_id,
                    userid=user_id,
                    authcredentialsconfigparams='username=' + user_name + '&password=' + user_password)
                self.get_zap.users.set_user_enabled(contextid=context_id, userid=user_id, enabled=True)

            if ("forced" in user and user["forced"]):
                logging.debug("Configuring a forced user '%s' with id, for context(%s)'", user_id, context_id)
                self.get_zap.forcedUser.set_forced_user(contextid=context_id, userid=user_id)
                self.get_zap.forcedUser.set_forced_user_mode_enabled(True)

    def _configure_context_session_management(self, sessions_config: collections.OrderedDict, context_id: int):
        """Protected method to configure the ZAP 'Context / Session Mannagement' Settings based on a given ZAP config.
        
        Parameters
        ----------
        sessions : collections.OrderedDict
            The current sessions configuration object containing the ZAP sessions configuration (based on the class ZapConfiguration).
        context_id : int
            The zap context id tot configure the ZAP authentication for (based on the class ZapConfiguration).
        """

        sessions_type = sessions_config["type"]
        
        logging.info("Configuring the ZAP session management (type=%s)", sessions_type)
        if sessions_type == "cookieBasedSessionManagement":
            logging.debug("Configuring cookieBasedSessionManagement")
            self.get_zap.sessionManagement.set_session_management_method(
                contextid=context_id,
                methodname='cookieBasedSessionManagement')
        elif sessions_type == "httpAuthSessionManagement":
            logging.debug("Configuring httpAuthSessionManagement")
            self.get_zap.sessionManagement.set_session_management_method(
                contextid=context_id,
                methodname='httpAuthSessionManagement')
        elif sessions_type == "scriptBasedSessionManagement":
            logging.debug("Configuring scriptBasedSessionManagement()")
            if("scriptBasedSessionManagement" in sessions_config):
                script_config = sessions_config["scriptBasedSessionManagement"]
                logging.debug("Script Config: %s", str(script_config))
                if(not script_config == None and "scriptName" in script_config and "scriptFilePath" in script_config and "scriptEngine" in script_config):
                    self._configure_load_script(script_config=script_config, script_type="session")
                    # Here they say that only "cookieBasedSessionManagement"; "httpAuthSessionManagement"
                    # is possible, but maybe this is outdated and it works anyway, hopefully:
                    # https://github.com/zaproxy/zap-api-python/blob/9bab9bf1862df389a32aab15ea4a910551ba5bfc/src/examples/zap_example_api_script.py#L97
                    session_params = ('scriptName=' + script_config["scriptName"])
                    self.get_zap.sessionManagement.set_session_management_method(
                        contextid=context_id,
                        methodname='scriptBasedSessionManagement',
                        methodconfigparams=session_params)
                else:
                    logging.warning("Important script authentication configs (scriptName, scriptFilePath, scriptEngine) are missing! Ignoring the authenication script configuration. Please check your YAML configuration.")
            else:
                    logging.warning("The 'scriptBasedSessionManagement' configuration section is missing but you have activated it (type: scriptBasedSessionManagement)! Ignoring the script configuration for session management. Please check your YAML configuration.")

    def _configure_context_technologies(self, technology: collections.OrderedDict, context_name: str):
        """Protected method to configure the ZAP 'Context / Technology' Settings based on a given ZAP config.
        
        Parameters
        ----------
        technology : collections.OrderedDict
            The current technology configuration object containing the ZAP technology configuration (based on the class ZapConfiguration).
        context_id : int
            The zap context id tot configure the ZAP authentication for (based on the class ZapConfiguration).
        """

        if(technology):
            # Remove all existing ZAP Users for given context
            #logging.warning("Existing technologies ' %s' will be removed for context: %s", zap.context.technology_list, context_name)
            #zap.context.exclude_all_context_technologies(contextname=context_name)
            
            if "included" in technology:
                technologies = ", ".join(technology["included"])
                logging.debug("Include technologies '%s' in context with name %s", technologies, context_name)
                self.get_zap.context.include_context_technologies(contextname=context_name, technologynames=technologies)
            
            if "excluded" in technology:
                technologies = ", ".join(technology["included"])
                logging.debug("Exclude technologies '%s' in context with name %s", technologies, context_name)
                self.get_zap.context.exclude_context_technologies(contextname=context_name, technologynames=technologies)
