package cn.makinnet.springboot;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // 这个注解表示为组件类，SpringBoot启动时会加载
@ConfigurationProperties(prefix = "app") //将 app 开头的配置项自动赋值给bean属性
public class AppConfig {

    private String name;
    private String version;
    private String description;
    private Integer pageSize;
    private Boolean showAdvert;
    private String website;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getShowAdvert() {
        return showAdvert;
    }

    public void setShowAdvert(Boolean showAdvert) {
        this.showAdvert = showAdvert;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


}
