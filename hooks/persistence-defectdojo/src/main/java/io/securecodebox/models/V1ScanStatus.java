/*
 * Kubernetes
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1.18.2
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.securecodebox.models;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.securecodebox.models.V1ScanStatusFindings;
import io.securecodebox.models.V1ScanStatusReadAndWriteHookStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ScanStatus defines the observed state of Scan
 */
@ApiModel(description = "ScanStatus defines the observed state of Scan")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-03-26T19:41:39.505Z[Etc/UTC]")
public class V1ScanStatus {
  public static final String SERIALIZED_NAME_ERROR_DESCRIPTION = "errorDescription";
  @SerializedName(SERIALIZED_NAME_ERROR_DESCRIPTION)
  private String errorDescription;

  public static final String SERIALIZED_NAME_FINDING_DOWNLOAD_LINK = "findingDownloadLink";
  @SerializedName(SERIALIZED_NAME_FINDING_DOWNLOAD_LINK)
  private String findingDownloadLink;

  public static final String SERIALIZED_NAME_FINDINGS = "findings";
  @SerializedName(SERIALIZED_NAME_FINDINGS)
  private V1ScanStatusFindings findings;

  public static final String SERIALIZED_NAME_FINISHED_AT = "finishedAt";
  @SerializedName(SERIALIZED_NAME_FINISHED_AT)
  private String finishedAt;

  public static final String SERIALIZED_NAME_RAW_RESULT_DOWNLOAD_LINK = "rawResultDownloadLink";
  @SerializedName(SERIALIZED_NAME_RAW_RESULT_DOWNLOAD_LINK)
  private String rawResultDownloadLink;

  public static final String SERIALIZED_NAME_RAW_RESULT_FILE = "rawResultFile";
  @SerializedName(SERIALIZED_NAME_RAW_RESULT_FILE)
  private String rawResultFile;

  public static final String SERIALIZED_NAME_RAW_RESULT_TYPE = "rawResultType";
  @SerializedName(SERIALIZED_NAME_RAW_RESULT_TYPE)
  private String rawResultType;

  public static final String SERIALIZED_NAME_READ_AND_WRITE_HOOK_STATUS = "readAndWriteHookStatus";
  @SerializedName(SERIALIZED_NAME_READ_AND_WRITE_HOOK_STATUS)
  private List<V1ScanStatusReadAndWriteHookStatus> readAndWriteHookStatus = null;

  public static final String SERIALIZED_NAME_STATE = "state";
  @SerializedName(SERIALIZED_NAME_STATE)
  private String state;


  public V1ScanStatus errorDescription(String errorDescription) {
    
    this.errorDescription = errorDescription;
    return this;
  }

   /**
   * Get errorDescription
   * @return errorDescription
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getErrorDescription() {
    return errorDescription;
  }


  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }


  public V1ScanStatus findingDownloadLink(String findingDownloadLink) {
    
    this.findingDownloadLink = findingDownloadLink;
    return this;
  }

   /**
   * FindingDownloadLink link to download the finding json file from. Valid for 7 days
   * @return findingDownloadLink
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "FindingDownloadLink link to download the finding json file from. Valid for 7 days")

  public String getFindingDownloadLink() {
    return findingDownloadLink;
  }


  public void setFindingDownloadLink(String findingDownloadLink) {
    this.findingDownloadLink = findingDownloadLink;
  }


  public V1ScanStatus findings(V1ScanStatusFindings findings) {
    
    this.findings = findings;
    return this;
  }

   /**
   * Get findings
   * @return findings
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public V1ScanStatusFindings getFindings() {
    return findings;
  }


  public void setFindings(V1ScanStatusFindings findings) {
    this.findings = findings;
  }


  public V1ScanStatus finishedAt(String finishedAt) {
    
    this.finishedAt = finishedAt;
    return this;
  }

   /**
   * FinishedAt contains the time where the scan (including parser &amp; hooks) has been marked as \&quot;Done\&quot;
   * @return finishedAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "FinishedAt contains the time where the scan (including parser & hooks) has been marked as \"Done\"")

  public String getFinishedAt() {
    return finishedAt;
  }


  public void setFinishedAt(String finishedAt) {
    this.finishedAt = finishedAt;
  }


  public V1ScanStatus rawResultDownloadLink(String rawResultDownloadLink) {
    
    this.rawResultDownloadLink = rawResultDownloadLink;
    return this;
  }

   /**
   * RawResultDownloadLink link to download the raw result file from. Valid for 7 days
   * @return rawResultDownloadLink
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "RawResultDownloadLink link to download the raw result file from. Valid for 7 days")

  public String getRawResultDownloadLink() {
    return rawResultDownloadLink;
  }


  public void setRawResultDownloadLink(String rawResultDownloadLink) {
    this.rawResultDownloadLink = rawResultDownloadLink;
  }


  public V1ScanStatus rawResultFile(String rawResultFile) {
    
    this.rawResultFile = rawResultFile;
    return this;
  }

   /**
   * RawResultFile Filename of the result file of the scanner. e.g. &#x60;nmap-result.xml&#x60;
   * @return rawResultFile
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "RawResultFile Filename of the result file of the scanner. e.g. `nmap-result.xml`")

  public String getRawResultFile() {
    return rawResultFile;
  }


  public void setRawResultFile(String rawResultFile) {
    this.rawResultFile = rawResultFile;
  }


  public V1ScanStatus rawResultType(String rawResultType) {
    
    this.rawResultType = rawResultType;
    return this;
  }

   /**
   * RawResultType determines which kind of ParseDefinition will be used to turn the raw results of the scanner into findings
   * @return rawResultType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "RawResultType determines which kind of ParseDefinition will be used to turn the raw results of the scanner into findings")

  public String getRawResultType() {
    return rawResultType;
  }


  public void setRawResultType(String rawResultType) {
    this.rawResultType = rawResultType;
  }


  public V1ScanStatus readAndWriteHookStatus(List<V1ScanStatusReadAndWriteHookStatus> readAndWriteHookStatus) {
    
    this.readAndWriteHookStatus = readAndWriteHookStatus;
    return this;
  }

  public V1ScanStatus addReadAndWriteHookStatusItem(V1ScanStatusReadAndWriteHookStatus readAndWriteHookStatusItem) {
    if (this.readAndWriteHookStatus == null) {
      this.readAndWriteHookStatus = new ArrayList<V1ScanStatusReadAndWriteHookStatus>();
    }
    this.readAndWriteHookStatus.add(readAndWriteHookStatusItem);
    return this;
  }

   /**
   * Get readAndWriteHookStatus
   * @return readAndWriteHookStatus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<V1ScanStatusReadAndWriteHookStatus> getReadAndWriteHookStatus() {
    return readAndWriteHookStatus;
  }


  public void setReadAndWriteHookStatus(List<V1ScanStatusReadAndWriteHookStatus> readAndWriteHookStatus) {
    this.readAndWriteHookStatus = readAndWriteHookStatus;
  }


  public V1ScanStatus state(String state) {
    
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getState() {
    return state;
  }


  public void setState(String state) {
    this.state = state;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1ScanStatus v1ScanStatus = (V1ScanStatus) o;
    return Objects.equals(this.errorDescription, v1ScanStatus.errorDescription) &&
        Objects.equals(this.findingDownloadLink, v1ScanStatus.findingDownloadLink) &&
        Objects.equals(this.findings, v1ScanStatus.findings) &&
        Objects.equals(this.finishedAt, v1ScanStatus.finishedAt) &&
        Objects.equals(this.rawResultDownloadLink, v1ScanStatus.rawResultDownloadLink) &&
        Objects.equals(this.rawResultFile, v1ScanStatus.rawResultFile) &&
        Objects.equals(this.rawResultType, v1ScanStatus.rawResultType) &&
        Objects.equals(this.readAndWriteHookStatus, v1ScanStatus.readAndWriteHookStatus) &&
        Objects.equals(this.state, v1ScanStatus.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorDescription, findingDownloadLink, findings, finishedAt, rawResultDownloadLink, rawResultFile, rawResultType, readAndWriteHookStatus, state);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1ScanStatus {\n");
    sb.append("    errorDescription: ").append(toIndentedString(errorDescription)).append("\n");
    sb.append("    findingDownloadLink: ").append(toIndentedString(findingDownloadLink)).append("\n");
    sb.append("    findings: ").append(toIndentedString(findings)).append("\n");
    sb.append("    finishedAt: ").append(toIndentedString(finishedAt)).append("\n");
    sb.append("    rawResultDownloadLink: ").append(toIndentedString(rawResultDownloadLink)).append("\n");
    sb.append("    rawResultFile: ").append(toIndentedString(rawResultFile)).append("\n");
    sb.append("    rawResultType: ").append(toIndentedString(rawResultType)).append("\n");
    sb.append("    readAndWriteHookStatus: ").append(toIndentedString(readAndWriteHookStatus)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
