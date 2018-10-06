# Pixiv-Illustration-Collection
## Bright Point:
前后端完全分离\<br>
三图床混合优化图片加载体验\<br>
Nginx反向代理(作为跳板优化延迟与tomcat集群动静分离)\<br>
原生js\<br>
PC端移动端单独适配(Nginx UA判断跳转)\<br>
爬虫业务与web业务服务端分别分布\<br>
SSH隧道连接数据库(安全性)\<br>
Httpclient连接池,线程池队列,Druid数据库连接池\<br>
## 伺服器:
本地arm服务器:作为爬虫服务器,每日五点爬取pixiv三天前的日排行数据\<br>
Aws lightsail:反向代理图片请求过程中添加referer请求头,绕过防盗链\<br>
Uovz香港:反向代理pixiv搜索api,加速访问,延迟提升500ms\<br>
息壤 北京:作为主web服务器,nginx tomcat动静分离\<br>
