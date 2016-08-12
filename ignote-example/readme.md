* {IGNITE_HOME}\examples\config\example-default.xml 中有設定 H2 datasource
       如果 pom.xml 裡面沒有加入 h2 driver 的話, 會無法執行
* Ignite 預設的 cacheMode 是 PARTITIONED, 為了避免程式執行結束後 cache 中的資料遺失, 可以
    1. 將程式端的 clientMode 設定為 true
    2. 將程式端的 cacheMode 設定為 REPLICATED
    3. enable PARTITIONED 模式的備份功能 <property name="backups" value="1"/>
* 把 {IGNITE_HOME}\libs\optional 中的  ignite-rest-http 複製到   {IGNITE_HOME}\libs\ 下面, 
       再執行   {IGNITE_HOME}\bin\ingite.{bat|sh}, 即可啟用 ignite rest 功能
* 如果要在 server 端啟用 datasource 的話, 必須把 jdbc driver jar 放到  {IGNITE_HOME}\libs 下
* 使用   {IGNITE_HOME}\bin\ignite-schema-import.{bat|sh} 可以產生需要的 pojo class 跟  configuration
* set IGNITE_HOME={IGNITE_HOME}

  {IGNITE_HOME}\bin\ignite.{bat|sh} examples/config/example-ignite.xml