package kr.ac.castcommunity.cc.models

class Board {
<<<<<<< HEAD
<<<<<<< HEAD
    var bnum: Int? = null
    var byte : String? = null
=======
=======
>>>>>>> parent of 65318ca (merged)

    var id: String? = null
    var title: String? = null
>>>>>>> parent of 65318ca (merged)
    var writer: String? = null
    var title: String? = null
    var content : String? = null
    var memId : String? = null
    var time: String? = null
    var cnt: Int? = null
    var anonymous : Int? = null

<<<<<<< HEAD
    constructor(
        bnum: Int?,
        byte: String?,
        writer: String?,
        title: String?,
        content: String?,
        memId: String?,
        time: String?,
        cnt: Int?,
        anonymous: Int?
    ) {
        this.bnum = bnum
        this.byte = byte
        this.writer = writer
=======
    constructor() {

    }

    constructor(id: String, title: String, contents: String, writer: String, time: String) {
        this.id = id
        this.title = title
        this.contents = contents
        this.time = time
        this.writer = writer


    }

    constructor(id: String, title: String, writer: String, contents: String, am: String, img: String) {
        this.id = id
<<<<<<< HEAD
>>>>>>> parent of 65318ca (merged)
=======
>>>>>>> parent of 65318ca (merged)
        this.title = title
        this.content = content
        this.memId = memId
        this.time = time
        this.cnt = cnt
        this.anonymous = anonymous
    }

    override fun toString(): String {
<<<<<<< HEAD
        return "Board(bnum=$bnum, byte=$byte, writer=$writer, title=$title, content=$content, memId=$memId, time=$time, cnt=$cnt, anonymous=$anonymous)"
=======
        return "Board{" +
                "id='" + id + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", writer='" + writer + '\''.toString() +
                ", contents='" + contents + '\''.toString() +
                ", am='" + am + '\''.toString() +
                ", img='" + img + '\''.toString() +
                '}'.toString()
>>>>>>> parent of 65318ca (merged)
    }


}
