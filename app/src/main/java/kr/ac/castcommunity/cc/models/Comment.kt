package kr.ac.castcommunity.cc.models

class Comment {

    var boardid: Int? = null
    var commentid: Int? = null
    var memId : String? = null
    var writer: String? = null
    var content: String? = null
    var time: String? = null
    var anonymous : Int? = null

    constructor(
        boardid: Int?,
        commentid: Int?,
        memId: String?,
        writer: String?,
        content: String?,
        time: String?,
        anonymous: Int?
    ) {
        this.boardid = boardid
        this.commentid = commentid
        this.memId = memId
        this.writer = writer
        this.content = content
        this.time = time
        this.anonymous = anonymous
    }

    override fun toString(): String {
        return "Comment(boardid=$boardid, commentid=$commentid, memId=$memId, writer=$writer, content=$content, time=$time, anonymous=$anonymous)"
    }
}
