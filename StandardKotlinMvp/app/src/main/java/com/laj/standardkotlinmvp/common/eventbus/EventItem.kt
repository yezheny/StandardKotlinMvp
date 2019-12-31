package com.laj.standardkotlinmvp.common.eventbus

/**
 * desc:
 * author: aragon
 * date: 2018/05/02
 */

class EventItem {

    var obj: Any? = null
    var msgType: Int
    var receiveObj: Int

    constructor(receiveObj: Int, msgType: Int) {
        this.msgType = msgType
        this.receiveObj = receiveObj
    }

    constructor(receiveObj: Int, msgType: Int, obj: Any) {
        this.obj = obj
        this.msgType = msgType
        this.receiveObj = receiveObj
    }
}
