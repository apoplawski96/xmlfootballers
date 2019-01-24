package com.example.arturpoplawski.xmlparser

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

data class Footballer(val firstName: String?, val lastName: String?, val age: String?, val nationality: String?, val preferredPosition: String?,
                      val secondaryPosition: String?, val footballerImageUrl: String?, val flagImageUrl: String?, val footballClub: String?, val teamNumber:
                        String?, val isBenched: String?)

class XmlParser{

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Footballer>{
        inputStream.use { inputStream ->
            val parser : XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readPlayersXmlDatabase(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPlayersXmlDatabase(parser: XmlPullParser): List<Footballer> {
        val entries = mutableListOf<Footballer>()

        parser.require(XmlPullParser.START_TAG, ns, "playersXmlDatabase")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "player") {
                entries.add(readPlayersData(parser))
            } else {
                skip(parser)
            }
        }

        return entries
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPlayersData(parser: XmlPullParser): Footballer {
        parser.require(XmlPullParser.START_TAG, ns, "player")
        var firstName: String? = null
        var lastName: String? = null
        var age: String? = null
        var nationality: String? = null
        var preferredPosition: String? = null
        var secondaryPosition: String? = null
        var footballerImageUrl: String? = null
        var flagImageUrl: String? = null
        var footballClub: String? = null
        var teamNumber: String? = null
        var isBenched: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "firstName" -> firstName = readXmlElement(parser, "firstName")
                "lastName" -> lastName = readXmlElement(parser, "lastName")
                "age" -> age = readXmlElement(parser, "age")
                "nationality" -> nationality = readXmlElement(parser, "nationality")
                "preferredPosition" -> preferredPosition = readXmlElement(parser, "preferredPosition")
                //"secondaryPosition" -> secondaryPosition = readSecPosition(parser)
                "imageURL" -> footballerImageUrl = readXmlElement(parser, "imageURL")
                "flagURL" -> flagImageUrl = readXmlElement(parser, "flagURL")
                "footballClub" -> footballClub = readXmlElement(parser, "footballClub")
                "teamNumber" -> teamNumber = readXmlElement(parser, "teamNumber")
                "benched" -> isBenched = readXmlElement(parser, "benched")
                else -> skip(parser)
            }
        }
        return Footballer(firstName, lastName, age, nationality, preferredPosition, secondaryPosition, footballerImageUrl, flagImageUrl, footballClub, teamNumber, isBenched)
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readXmlElement(parser: XmlPullParser, elementName : String): String {
        parser.require(XmlPullParser.START_TAG, ns, elementName)
        val xmlElement = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, elementName)
        return xmlElement
    }

    // For the tags title and summary, extracts their text values.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }



}