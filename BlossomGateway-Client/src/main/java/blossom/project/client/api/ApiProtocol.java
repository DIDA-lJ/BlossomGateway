package blossom.project.client.api;

public enum ApiProtocol {
    HTTP("http", "http协议"),
    DUBBO("dubbo", "dubbo协议");

    private String protocol;

    private String desc;

    ApiProtocol(String protocol, String desc) {
        this.protocol = protocol;
        this.desc = desc;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDesc() {
        return desc;
    }
}
