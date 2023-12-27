package com.cccd.io.sdk.capture.repositories.mrz_reader

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import net.sf.scuba.data.Gender
import org.jmrtd.lds.icao.MRZInfo
import java.util.Locale
import java.util.regex.Pattern

class MRZReaderRepositoryImpl : MRZReaderRepository {

    val patternLine1 =
        Pattern.compile("[0-9IDVNM]{5}(?<documentNumber>[0-9ILDSOG]{9})(?<checkDigitDocumentNumber>[0-9ILDSOG]{1})(?<fullDocumentNumber>[0-9ILDSOG]{12})[A-Z<]{2}[0-9]{1}")
    val patternLine2 =
        Pattern.compile("(?<dateOfBirth>[0-9ILDSOG]{6})(?<checkDigitDateOfBirth>[0-9ILDSOG]{1})(?<sex>[FM<]){1}(?<expirationDate>[0-9ILDSOG]{6})(?<checkDigitExpiration>[0-9ILDSOG]{1})(?<nationality>[A-Z<]{3}).+[0-9]{1}")
    val patternLine3 = Pattern.compile("[-]\\w+[A-Z][<]{1}[A-Z<]\\w+[A-Z<]{1}.+[-]")

    private fun createMRZTD(
        issuingState: String,
        documentNumber: String,
        dateOfBirth: String,
        gender: Gender,
        dateOfExpiry: String,
        nationality: String
    ): MRZInfo {
        return MRZInfo.createTD1MRZInfo(
            "I",
            issuingState,
            documentNumber,
            null,
            dateOfBirth,
            gender,
            dateOfExpiry,
            nationality,
            null,
            "primaryIdentifier",
            "secondaryIdentifier"
        )
    }

    private fun cleanDate(date: String): String {
        var tempDate = date
        tempDate = tempDate.replace("I".toRegex(), "1")
        tempDate = tempDate.replace("L".toRegex(), "1")
        tempDate = tempDate.replace("D".toRegex(), "0")
        tempDate = tempDate.replace("O".toRegex(), "0")
        tempDate = tempDate.replace("S".toRegex(), "5")
        tempDate = tempDate.replace("G".toRegex(), "6")
        return tempDate
    }

    override fun process(
        bitmap: Bitmap,
        mrzCallback: MRZCallback
    ) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                var fullRead = ""
                val blocks = visionText.textBlocks
                for (i in blocks.indices) {
                    var temp = ""
                    val lines = blocks[i].lines
                    for (j in lines.indices) {
                        temp += lines[j].text + "-"
                    }
                    temp = temp.replace("\r".toRegex(), "").replace("\n".toRegex(), "")
                        .replace("\t".toRegex(), "").replace(" ", "")
                    fullRead += "$temp-"
                }
                fullRead = fullRead.uppercase(Locale.getDefault())

                val matcherLineIeIDTypeLine1 = patternLine1.matcher(fullRead)
                val matcherLineIeIDTypeLine2 = patternLine2.matcher(fullRead)
                val matcherLineIeIDTypeLine3 = patternLine3.matcher(fullRead)

                if (matcherLineIeIDTypeLine1.find() && matcherLineIeIDTypeLine2.find() && matcherLineIeIDTypeLine3.find()) {
                    val documentNumber = cleanDate(matcherLineIeIDTypeLine1.group(1))
                    val dateOfBirthDay = cleanDate(matcherLineIeIDTypeLine2.group(1))
                    val sex = matcherLineIeIDTypeLine2.group(3)
                    val dateOfExpiry = cleanDate(matcherLineIeIDTypeLine2.group(4))
                    val nationality = matcherLineIeIDTypeLine2.group(6)


                    var gender = Gender.UNKNOWN
                    if (sex.equals("M")) {
                        gender = Gender.MALE
                    } else if (sex.equals("F")) {
                        gender = Gender.FEMALE
                    }
                    val mrzInfo = createMRZTD(
                        issuingState = nationality,
                        documentNumber = documentNumber,
                        dateOfBirth = dateOfBirthDay,
                        gender = gender,
                        dateOfExpiry = dateOfExpiry,
                        nationality = nationality
                    )
                    mrzCallback.onMRZRead(mrzInfo)
                } else {
                    mrzCallback.onMRZReadFailure()
                }
            }
            .addOnFailureListener { e ->
                mrzCallback.onFailure(e)
            }
    }
}