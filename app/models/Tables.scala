package models

import slick.jdbc.MySQLProfile.api._
import java.sql.Timestamp

class Recipes(tag: Tag) extends Table[Recipe](tag, "recipes") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def makingTime = column[String]("making_time")
  def serves = column[String]("serves")
  def ingredients = column[String]("ingredients")
  def cost = column[Long]("cost")
  def createdAt = column[String]("created_at")
  def updatedAt = column[String]("updated_at")



  def * =
    (id.?, title, makingTime, serves, ingredients, cost,createdAt,updatedAt)
      .<>(Recipe.tupled, Recipe.unapply)
}

object Tables {
  val recipes = TableQuery[Recipes]
}