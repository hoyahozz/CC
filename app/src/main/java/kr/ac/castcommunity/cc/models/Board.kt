package kr.ac.castcommunity.cc.models

class Board {

    var id: String? = null
    var title: String? = null
    var writer: String? = null
    var contents: String? = null
    var am: String? = null
    var img: String? = null
    var time: String? = null

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
        this.title = title
        this.writer = writer
        this.contents = contents
        this.am = am
        this.img = img
    }

    override fun toString(): String {
        return "Board{" +
                "id='" + id + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", writer='" + writer + '\''.toString() +
                ", contents='" + contents + '\''.toString() +
                ", am='" + am + '\''.toString() +
                ", img='" + img + '\''.toString() +
                '}'.toString()
    }
}
