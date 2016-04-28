package com.qunar.flight.autofile.pojo;

/**
 * Created by zhouxi.zhou on 2016/4/27.
 */
public class AutofileRequest {
    /**
     * jar包属性
     */

    String groupId;
    String artifactId;
    String version;



    /**
     * 接口名和方法名
     */
    String interfaceName;
    String methodName;
    /**
     * json 或者 xml
     */
    String type;
    public AutofileRequest(){

    }
    public AutofileRequest(String groupId, String artifactId, String version, String interfaceName, String methodName, String type) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
