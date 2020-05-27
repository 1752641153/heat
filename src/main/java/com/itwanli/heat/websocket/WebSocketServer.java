package com.itwanli.heat.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/ws/{userId}")
@Slf4j
@Component
public class WebSocketServer {

    //这里需要注意我发现在实际的开发中无法注入，解决办法在问题中说明
	    /*@Autowired
	    private SnapRecordRepository snapRecordRepository;

	    @Autowired
	    private ResourcesService resourcesService;*/

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String dateStr;

    private String userId;
    private List<String> cameraIndexCodes;

    /**
     * 收到请求后调用的方法
     * @param session
     */
    @OnMessage
    public void handleMessage(Session session, String message){
        log.info("收到客户端的ws请求");
    }

    /**
     * 连接建立成功调用的方法
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId) {
        log.info("建立webSocket的连接");
        this.session = session;
        Date date = new Date(System.currentTimeMillis());
        //this.dateStr = DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss");
        this.userId = userId;
        //List<String> cameraIndexCodes = resourcesService.getCameraIndexCodeFromXres(ResourcesTypeConstant.CAMERA, userId);
	        /*if (cameraIndexCodes.size() < 1){
	            cameraIndexCodes.add("");
	        }*/
        //this.cameraIndexCodes = cameraIndexCodes;
        webSocketSet.add(this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("关闭webSocket的连接");
        webSocketSet.remove(this);
    }


    /**
     * 发生错误时调用的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        webSocketSet.forEach(x -> {
            //List<SnapRecordEntity> snapRecordEntities = snapRecordRepository.findByCameraIdInOrderByEventTimeLimit(x.cameraIndexCodes,x.dateStr);
            //String message = JSON.toJSONString(BaseResult.success(snapRecordEntities)) ;
            try {
                x.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }




}
