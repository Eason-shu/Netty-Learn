- 整个的IO处理操作环节的前后两个环节，包括从通道读数据包和由通道发送到对端，由Netty的底层负责完成，不需要用户程序负责。
- 用户程序主要涉及的Handler环节为：数据包解码、业务处理、目标数据编码、把数据包写到通道中。
![img.png](img.png)