#  java操作 selenium 

## 封装了selenium 点击，鼠标滑动，获取input标签数值，切换窗口，切换iframe等方法
## 提供将jar包注册为windows服务
### 采用开源工具，改工具个人经过测试比较好用，详情见：https://github.com/kohsuke/winsw
    使用方法：
        1. 根据windows  dotnet版本下载对应的可执行文件和配置文件，
            exe下载地址：http://repo.jenkins-ci.org/releases/com/sun/winsw/winsw/
            配置文件下载地址：https://github.com/kohsuke/winsw/tree/master/examples
        2. 将exe和xml配置文件放在和jar包相同目录（exe和xml名称修改为相同名称）
            配置文件配置方法见：https://github.com/kohsuke/winsw/blob/master/doc/xmlConfigFile.md
        3. 指定 xxx.exe install 即可将jar包注册为windows服务
        4. xxx.exe unintall 即可将jar服务包卸载
        5. 服务install成功后可输入： windows + r ，然后输入services.msc，查看系统存在的服务，根据xml配置查找自己的服务，进行启动可关闭
            
            注： 
               1. 可以使用 xxx.exe start / stop 启动或停止服务
               2. 可采用assembly插件将工具和jar包打在同一目录，方便操作
