package io.cafebabe.http.api

/**
 * @author Vladimir Konstantinov
 * @version 1.0 (6/8/2015)
 */
sealed trait WsMessage

case class ConnectWsMessage(host: String) extends WsMessage
case object DisconnectWsMessage extends WsMessage
case class TextWsMessage(text: String) extends WsMessage
case class BinaryWsMessage(bytes: Array[Byte]) extends WsMessage