package kr.ac.castcommunity.cc.models

class Board {

    var bnum: Int? = null
    var title: String? = null
    var writer: String? = null
    var contents: String? = null
    var am: String? = null
    var img: String? = null
    var time: String? = null

    constructor() {

    }

    constructor(bnum: Int, title: String, contents: String, time: String, writer : String) {
        this.bnum = bnum
        this.title = title
        this.contents = contents
        this.time = time
        this.writer = writer
    }
/*
    constructor(bnum: String, title: String, writer: String, contents: String, am: String, img: String) {
        this.bnum = bnum
        this.title = title
        this.writer = writer
        this.contents = contents
        this.am = am
        this.img = img
    }
*/
    override fun toString(): String {
        return "Board{" +
                "bnum='" + bnum + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", writer='" + writer + '\''.toString() +
                ", contents='" + contents + '\''.toString() +
                ", am='" + am + '\''.toString() +
                ", img='" + img + '\''.toString() +
                '}'.toString()
    }
}
