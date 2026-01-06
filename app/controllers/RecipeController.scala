package controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

import dtos.CreateRecipeDto
import models.{Recipe,ApiResponse}
import services.RecipeService
import utils.ApiError

@Singleton
class RecipeController @Inject()(
                                     cc: ControllerComponents,
                                     recipesService: RecipeService
                                   )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val recipeFormat: OFormat[Recipe] = Json.format[Recipe]


  def create: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[CreateRecipeDto].fold(
      errors =>
        Future.successful(
          Ok(
            Json.obj(
              "message"  -> "Recipe creation failed!",
              "required" -> "title, making_time, serves, ingredients, cost"
            )
          )
        ),

      dto =>
        recipesService.create(dto).map {
          _.fold(
            _.toResult,
            c =>
              Ok(
                Json.toJson(
                  ApiResponse.success(
                    "Recipe successfully created!",
                    Json.arr(c)
                  )
                )
              )
          )
        }
    )
  }

  def listAll(): Action[AnyContent] = Action.async {
    recipesService.listAll()
      .map(_.fold(_.toResult, cs => Ok(Json.obj("recipes" -> Json.toJson(cs)))))
  }

  def findById(id: Int): Action[AnyContent] = Action.async {
    recipesService.findById(id).map(e => e.fold(_.toResult, c => Ok(
      Json.toJson(
        ApiResponse.success(
          "Recipe details by id",
          Json.arr(c)
        )
      )
    )))
  }

  def delete(id: Int): Action[AnyContent] = Action.async {
    recipesService.delete(id).map(e=> {
      if (e == 0)
      Ok(
        Json.obj(
          "message" -> "No recipe found"
        )
      )
      else
        Ok(
          Json.obj(
            "message" -> "Recipe successfully removed!"
          )
        )
    })
  }

  def update(id: Int): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[CreateRecipeDto].fold(
      errors => Future.successful(ApiError.InvalidJson(JsError(errors)).toResult),
      dto => recipesService.update(id, dto).map(e => e.fold(_.toResult, merged => Ok(Json.toJson(
        ApiResponse.success(
          "Recipe successfully updated!",
          Json.arr(merged)
        )
      ))))
    )
  }
}