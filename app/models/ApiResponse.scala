package models

import play.api.libs.json._
import java.time.Instant

/**
 * Standard API response wrapper
 *
 * @param success   indicates request success/failure
 * @param message   human-readable message
 * @param data      response payload (generic)
 * @param timestamp server-side response time
 */
case class ApiResponse[T](
                           message: String,
                           recipe: T,
                         )

object ApiResponse {

  /**
   * JSON serializer for ApiResponse
   * Works for any T that has an implicit Writes[T]
   */
  implicit def apiResponseWrites[T: Writes]: Writes[ApiResponse[T]] =
    Json.writes[ApiResponse[T]]

  /**
   * Helper constructors (optional but very useful)
   */
  def success[T](message: String, data: T): ApiResponse[T] =
    ApiResponse(message = message, recipe = data)

  def error(message: String): ApiResponse[JsObject] =
    ApiResponse(message = message, recipe = Json.obj())
}
