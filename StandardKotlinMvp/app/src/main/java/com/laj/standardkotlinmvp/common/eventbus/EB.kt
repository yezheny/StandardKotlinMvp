package com.laj.standardkotlinmvp.common.eventbus
import org.greenrobot.eventbus.EventBus

/**
 * desc:
 * author: aragon
 * date: 2018/05/02
 */

object EB {

    /**
     * 发送普通事件
     * @param receiveObj 事件接收者
     * @param msgType 事件类型
     */
    fun send(receiveObj: Int, msgType: Int) {
        EventBus.getDefault().post(EventItem(receiveObj, msgType))
    }

    /**
     * 发送普通事件
     * @param receiveObj 事件接收者
     * @param msgType 事件类型
     * @param ob 传递的参数
     */
    fun send(receiveObj: Int, msgType: Int, ob: Any) {
        EventBus.getDefault().post(EventItem(receiveObj, msgType, ob))
    }
}
