package kr.ac.castcommunity.cc.models

class Board {
    var bnum: Int? = null
    var btype: String? = null
    var writer: String? = null
    var title: String? = null
    var content: String? = null
    var memId: String? = null
    var time: String? = null
    var cnt: Int? = null
    var anonymous: Int? = null

    constructor(
        bnum: Int?,
        btype: String?,
        writer: String?,
        title: String?,
        content: String?,
        memId: String?,
        time: String?,
        cnt: Int?,
        anonymous: Int?
    ) {
        this.bnum = bnum
        this.btype = btype
        this.writer = writer
        this.title = title
        this.content = content
        this.memId = memId
        this.time = time
        this.cnt = cnt
        this.anonymous = anonymous
    }

    override fun toString(): String {
        return "Board(bnum=$bnum, btype=$btype, writer=$writer, title=$title, content=$content, memId=$memId, time=$time, cnt=$cnt, anonymous=$anonymous)"
    }


}
