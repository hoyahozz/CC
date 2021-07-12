package kr.ac.castcommunity.cc.models

class Comment {

    var boardid: Int? = null
    var commentid: Int? = null
    var writer: String? = null
    var contents: String? = null
    var time: String? = null

    constructor(boardid: Int, commentid: Int, writer: String, contents: String, time : String) {
        this.boardid = boardid
        this.commentid = commentid
        this.contents = contents
        this.time = time
        this.writer = writer
    }

    override fun toString(): String {
        return "Comment(boardid=$boardid, commentid=$commentid, writer=$writer, contents=$contents, time=$time)"
    }


}
