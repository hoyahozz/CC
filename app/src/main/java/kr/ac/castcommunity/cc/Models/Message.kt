package kr.ac.castcommunity.cc.Models

class Message {

    var messageroom : Int? = null
    var messagenum : Int? = null
    var sendnick : String? = null
    var receinick : String? = null
    var content : String? = null
    var date : String? = null

    constructor(
        messageroom: Int?,
        messagenum: Int?,
        sendnick: String?,
        receinick: String?,
        content: String?,
        date: String?
    ) {
        this.messageroom = messageroom
        this.messagenum = messagenum
        this.sendnick = sendnick
        this.receinick = receinick
        this.content = content
        this.date = date
    }

    constructor(
        messageroom: Int?,
        sendnick: String?,
        receinick: String?,
        content: String?,
        date: String?
    ) {
        this.messageroom = messageroom
        this.sendnick = sendnick
        this.receinick = receinick
        this.content = content
        this.date = date
    }

    override fun toString(): String {
        return "Message(messageroom=$messageroom, messagenum=$messagenum, sendnick=$sendnick, receinick=$receinick, content=$content, date=$date)"
    }


}
