package repositories

import models.{Recipe, Tables}
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

@Singleton
class RecipleRepository @Inject()(
                                     dbConfigProvider: DatabaseConfigProvider
                                   )(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val recipes = Tables.recipes
  def create(recipe: Recipe): Future[Recipe] =
    db.run(
      (recipes returning recipes.map(_.id)
        into ((recipe, id) => recipe.copy(id = Some(id)))
        ) += recipe
    )


  def listAll(): Future[Seq[Recipe]] =
    db.run(recipes.result)

  def findById(id: Int): Future[Option[Recipe]] =
    db.run(recipes.filter(_.id === id).result.headOption)

  def deleteById(id: Int): Future[Int] =
    db.run(recipes.filter(_.id === id).delete)

  def update(id: Int, updated: Recipe): Future[Int] =
    db.run(recipes.filter(_.id === id).update(updated))
}