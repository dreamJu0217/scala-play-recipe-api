package models


case class Recipe(
                           id: Option[Int] = None,
                           title: String,
                           makingTime: String,
                           serves: String,
                           ingredients: String,
                           cost: Long,
                           createdAt: String,
                           updatedAt: String
                         )