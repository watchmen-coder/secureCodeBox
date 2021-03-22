import { SlackNotifier } from "./SlackNotifier";
import axios from 'axios'
import { NotificationChannel } from "../model/NotificationChannel";
import { NotifierType } from "../NotifierType";
import { Scan } from "../model/Scan";

jest.mock('axios');

const channel: NotificationChannel = {
  name: "Channel Name",
  type: NotifierType.SLACK,
  templateName: "messageCard",
  rules: [],
  endPoint: "https://hooks.slack.com/services/<YOUR_TOKEN>"
};

const link = "https://hooks.slack.com/services/<YOUR_TOKEN>"

test("Should Send Message With Findings And Severities", async () => {

  const message = `{\"blocks\":[{\"type\":\"header\",\"text\":{\"type\":\"plain_text\",\"text\":\"New Nmap security scan results are available!\",\"emoji\":true}},{\"type\":\"context\",\"elements\":[{\"type\":\"image\",\"image_url\":\"https://www.securecodebox.io/favicon.png\",\"alt_text\":\"secureCodeBox Favicon\"},{\"type\":\"mrkdwn\",\"text\":\"Scan: demo-scan-1601086432\\nCreated at Fri Jan 01 2021 15:29:25 GMT+0100 (Central European Standard Time)\\n\"}]},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\"*Findings Severity Overview*:\\nhigh: 10\\nmedium: 5\\nlow: 2\\ninformational: 1\\n\"}]},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\"*Findings Category Overview*:\\nA Client Error response code was returned by the server: 1\\nInformation Disclosure - Sensitive Information in URL: 1\\nStrict-Transport-Security Header Not Set: 1\\n\"}]},{\"type\":\"actions\",\"elements\":[{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\"Open Kibana Dashboard\",\"emoji\":true},\"value\":\"click_me_123\",\"action_id\":\"actionId-0\",\"url\":\"https://securecodebox.io\"},{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\"Show Results in Kibana Dashboard\",\"emoji\":true},\"value\":\"click_me_123\",\"action_id\":\"actionId-1\",\"url\":\"https://securecodebox.io\"}]}]}`

  const scan: Scan = {
    metadata: {
      uid: "09988cdf-1fc7-4f85-95ee-1b1d65dbc7cc",
      name: "demo-scan-1601086432",
      namespace: "my-scans",
      creationTimestamp: new Date("2021-01-01T14:29:25Z"),
      labels: {
        company: "iteratec",
        "attack-surface": "external",
      },
    },
    spec: {
      scanType: "Nmap",
      parameters: ["-Pn", "localhost"],
    },
    status: {
      findingDownloadLink:
        "https://my-secureCodeBox-instance.com/scan-b9as-sdweref--sadf-asdfsdf-dasdgf-asdffdsfa7/findings.json",
      findings: {
        categories: {
          "A Client Error response code was returned by the server": 1,
          "Information Disclosure - Sensitive Information in URL": 1,
          "Strict-Transport-Security Header Not Set": 1,
        },
        count: 3,
        severities: {
          high: 10,
          medium: 5,
          low: 2,
          informational: 1,
        },
      },
      finishedAt: new Date("2020-05-25T02:38:13Z"),
      rawResultDownloadLink:
        "https://my-secureCodeBox-instance.com/scan-blkfsdg-sdgfsfgd-sfg-sdfg-dfsg-gfs98-e8af2172caa7/zap-results.json?Expires=1601691232",
      rawResultFile: "zap-results.json",
      rawResultType: "zap-json",
      state: "Done",
    },
  };

  const slackNotifier = new SlackNotifier(channel, scan, []);
  slackNotifier.sendMessage();
  expect(axios.post).toBeCalledWith(link, message);
});

test("Should Send Minimal Template For Empty Findings", async () => {
  const scan: Scan = {
    metadata: {
      uid: "09988cdf-1fc7-4f85-95ee-1b1d65dbc7cc",
      name: "demo-scan-1601086432",
      namespace: "my-scans",
      creationTimestamp: new Date("2021-01-01T14:29:25Z"),
      labels: {
        company: "iteratec",
        "attack-surface": "external",
      },
    },
    spec: {
      scanType: "Nmap",
      parameters: ["-Pn", "localhost"],
    },
    status: {
      findingDownloadLink:
        "https://my-secureCodeBox-instance.com/scan-b9as-sdweref--sadf-asdfsdf-dasdgf-asdffdsfa7/findings.json",
      findings: {},
      finishedAt: new Date("2020-05-25T02:38:13Z"),
      rawResultDownloadLink:
        "https://my-secureCodeBox-instance.com/scan-blkfsdg-sdgfsfgd-sfg-sdfg-dfsg-gfs98-e8af2172caa7/zap-results.json?Expires=1601691232",
      rawResultFile: "zap-results.json",
      rawResultType: "zap-json",
      state: "Done",
    },
  };
  const shortMessage = `{\"blocks\":[{\"type\":\"header\",\"text\":{\"type\":\"plain_text\",\"text\":\"New Nmap security scan results are available!\",\"emoji\":true}},{\"type\":\"context\",\"elements\":[{\"type\":\"image\",\"image_url\":\"https://www.securecodebox.io/favicon.png\",\"alt_text\":\"secureCodeBox Favicon\"},{\"type\":\"mrkdwn\",\"text\":\"Scan: demo-scan-1601086432\\nCreated at Fri Jan 01 2021 15:29:25 GMT+0100 (Central European Standard Time)\\n\"}]},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\"*Findings Severity Overview*:\\n\"}]},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\"*Findings Category Overview*:\\n\"}]},{\"type\":\"actions\",\"elements\":[{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\"Open Kibana Dashboard\",\"emoji\":true},\"value\":\"click_me_123\",\"action_id\":\"actionId-0\",\"url\":\"https://securecodebox.io\"},{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\"Show Results in Kibana Dashboard\",\"emoji\":true},\"value\":\"click_me_123\",\"action_id\":\"actionId-1\",\"url\":\"https://securecodebox.io\"}]}]}`

  const n = new SlackNotifier(channel, scan, []);
  n.sendMessage();
  expect(axios.post).toBeCalledWith(link, shortMessage);
})

test("Should Include Link To Kibana Dashboard", async () => {
  expect(true).toBe(false)
})

test("Should Include Link To DefectDojo Project", async () => {
  expect(true).toBe(false)
})

test("Should Send Minimal Template For Disabled VulnerabilityManagement", async () => {
  expect(true).toBe(false)
})

test("Should Send Minimal Template For Incomplete VulnerabilityManagement Configuration", async () => {
  expect(true).toBe(false)
})
