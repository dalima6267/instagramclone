package com.dalima.instagramclone.models

class Reel {
    var reelUrl:String=""
    var caption:String=""
    var profileLink:String?=""
    constructor()
    constructor(reelUrl: String, caption: String) {
        this.reelUrl = reelUrl
        this.caption = caption}

    constructor(reelUrl: String, caption: String, profileLink: String) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profileLink = profileLink
    }

}