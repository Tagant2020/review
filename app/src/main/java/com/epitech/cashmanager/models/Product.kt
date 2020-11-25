package com.epitech.cashmanager.models

open class Product(pId: Number, pName: String, pDetails: String, pImage: String) {
    private var id: Number = pId
    private var name: String = pName
    private var details: String = pDetails
    private var image: String = pImage
}