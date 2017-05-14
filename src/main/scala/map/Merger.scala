package map

import com.sksamuel.scrimage.{ Image, Position }

object Merger {
  def merge4(topLeft: Image, topRight: Image, bottomLeft: Image, bottomRight: Image): Image = {
    println(s"merging ${topLeft.width}")
    topLeft.resize(2, Position.TopLeft)
      .overlay(topRight, x = topLeft.width)
      .overlay(bottomLeft, y = topLeft.height)
      .overlay(bottomRight, x = topLeft.width, y = topLeft.height)
  }
}
