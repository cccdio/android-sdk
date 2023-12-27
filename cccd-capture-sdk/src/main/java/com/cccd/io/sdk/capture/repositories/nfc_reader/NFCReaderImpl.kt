package com.cccd.io.sdk.capture.repositories.nfc_reader

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import com.cccd.io.sdk.capture.jmrtd.MRTDTrustStore
import com.cccd.io.sdk.capture.models.data.AdditionalDocumentDetails
import com.cccd.io.sdk.capture.models.data.Eid
import com.cccd.io.sdk.capture.models.data.PersonAdditionalDetails
import com.cccd.io.sdk.capture.models.data.PersonDetails
import com.cccd.io.sdk.capture.models.data.PersonOptionalDetails
import com.cccd.io.sdk.capture.utils.EidNfcUtils
import net.sf.scuba.smartcards.CardService
import org.jmrtd.PassportService
import org.jmrtd.lds.icao.DG1File
import org.jmrtd.lds.icao.MRZInfo

class NFCReaderImpl : NFCReader {
    override fun read(
        context: Context,
        intent: Intent,
        mrzInfo: MRZInfo,
        eidCallback: EidCallback
    ) {

        if (intent.extras == null) {
            eidCallback.onEidReadFinish()
            return
        }
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG) ?: return

        var passportService: PassportService? = null


        try {
            val nfc = IsoDep.get(tag)
            nfc.timeout = Math.max(nfc.timeout, 2000)
            val cs = CardService.getInstance(nfc)

            passportService = PassportService(
                cs,
                PassportService.NORMAL_MAX_TRANCEIVE_LENGTH,
                PassportService.NORMAL_MAX_TRANCEIVE_LENGTH,
                PassportService.DEFAULT_MAX_BLOCKSIZE,
                false,
                true
            )
            passportService.open()

            eidCallback.onEidReadStart()

            val eidNfc = EidNfc(passportService, MRTDTrustStore(), mrzInfo, EidNfc.MAX_BLOCK_SIZE)

            val eid = Eid()

            eid.featureStatus = eidNfc.features
            eid.verificationStatus = eidNfc.verificationStatus
            eid.sodFile = eidNfc.sodFile

            // DG1 - Basic Information
            if (eidNfc.dg1File != null) {
                val mrzInfo = (eidNfc.dg1File as DG1File).mrzInfo
                val personBasicInfo = PersonDetails()
                personBasicInfo.dateOfBirth = mrzInfo.dateOfBirth
                personBasicInfo.dateOfExpiry = mrzInfo.dateOfExpiry
                personBasicInfo.documentCode = mrzInfo.documentCode
                personBasicInfo.documentNumber = mrzInfo.documentNumber
                personBasicInfo.optionalData1 = mrzInfo.optionalData1
                personBasicInfo.optionalData2 = mrzInfo.optionalData2
                personBasicInfo.issuingState = mrzInfo.issuingState
                personBasicInfo.primaryIdentifier = mrzInfo.primaryIdentifier
                personBasicInfo.secondaryIdentifier = mrzInfo.secondaryIdentifier
                personBasicInfo.nationality = mrzInfo.nationality
                personBasicInfo.gender = mrzInfo.gender
                eid.personDetails = personBasicInfo
            }

            // DG2 - Face Image
            if (eidNfc.dg2File != null) {
                try {
                    val faceImage = EidNfcUtils.retrieveFaceImage(eidNfc.dg2File!!)
                    eid.face = faceImage
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // DG3 - Fingerprint Image
            if (eidNfc.dg3File != null) {
                try {
                    val bitmaps = EidNfcUtils.retrieveFingerPrintImage(eidNfc.dg3File!!)
                    eid.fingerprints = bitmaps
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // DG5 - Portrait Image
            if (eidNfc.dg5File != null) {
                try {
                    val faceImage = EidNfcUtils.retrievePortraitImage(eidNfc.dg5File!!)
                    eid.portrait = faceImage
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // DG7 - Signature Image
            if (eidNfc.dg7File != null) {
                try {
                    val bitmap = EidNfcUtils.retrieveSignatureImage(context, eidNfc.dg7File!!)
                    eid.signature = bitmap
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // DG11 - Person Additional Details
            val dg11 = eidNfc.dg11File
            if (dg11 != null) {
                val personExtraInfo = PersonAdditionalDetails()
                personExtraInfo.custodyInformation = dg11.custodyInformation
                personExtraInfo.fullDateOfBirth = dg11.fullDateOfBirth
                personExtraInfo.nameOfHolder = dg11.nameOfHolder
                personExtraInfo.otherNames = dg11.otherNames
                personExtraInfo.otherNames = dg11.otherNames
                personExtraInfo.otherValidTDNumbers = dg11.otherValidTDNumbers
                personExtraInfo.permanentAddress = dg11.permanentAddress
                personExtraInfo.personalNumber = dg11.personalNumber
                personExtraInfo.personalSummary = dg11.personalSummary
                personExtraInfo.placeOfBirth = dg11.placeOfBirth
                personExtraInfo.profession = dg11.profession
                personExtraInfo.proofOfCitizenship = dg11.proofOfCitizenship
                personExtraInfo.tag = dg11.tag
                personExtraInfo.tagPresenceList = dg11.tagPresenceList
                personExtraInfo.telephone = dg11.telephone
                personExtraInfo.title = dg11.title
                eid.personAdditionalDetails = personExtraInfo
            }

            // DG12 - Additional Document Details
            val dg12 = eidNfc.dg12File
            if (dg12 != null) {
                val additionalDocumentDetails = AdditionalDocumentDetails()
                additionalDocumentDetails.dateAndTimeOfPersonalization =
                    dg12.dateAndTimeOfPersonalization
                additionalDocumentDetails.dateOfIssue = dg12.dateOfIssue
                additionalDocumentDetails.endorsementsAndObservations =
                    dg12.endorsementsAndObservations
                try {
                    val imageOfFront = dg12.imageOfFront
                    val bitmapImageOfFront =
                        BitmapFactory.decodeByteArray(imageOfFront, 0, imageOfFront.size)
                    additionalDocumentDetails.imageOfFront = bitmapImageOfFront
                } catch (e: Exception) {
                    eidCallback.onEidReadError(e)
                }
                try {
                    val imageOfRear = dg12.imageOfRear
                    val bitmapImageOfRear =
                        BitmapFactory.decodeByteArray(imageOfRear, 0, imageOfRear.size)
                    additionalDocumentDetails.imageOfRear = bitmapImageOfRear
                } catch (e: Exception) {
                    eidCallback.onEidReadError(e)
                }
                additionalDocumentDetails.issuingAuthority = dg12.issuingAuthority
                additionalDocumentDetails.namesOfOtherPersons = dg12.namesOfOtherPersons
                additionalDocumentDetails.personalizationSystemSerialNumber =
                    dg12.personalizationSystemSerialNumber
                additionalDocumentDetails.taxOrExitRequirements = dg12.taxOrExitRequirements
                eid.additionalDocumentDetails = additionalDocumentDetails
            }

            // DG13 - Person Optional Details
            val dg13 = eidNfc.dg13File
            if (dg13 != null) {
                val personOptionalDetails = PersonOptionalDetails()
                personOptionalDetails.eidNumber = dg13.eidNumber
                personOptionalDetails.fullName = dg13.fullName
                personOptionalDetails.dateOfBirth = dg13.dateOfBirth
                personOptionalDetails.gender = dg13.gender
                personOptionalDetails.nationality = dg13.nationality
                personOptionalDetails.ethnicity = dg13.ethnicity
                personOptionalDetails.religion = dg13.religion
                personOptionalDetails.placeOfOrigin = dg13.placeOfOrigin
                personOptionalDetails.placeOfResidence = dg13.placeOfResidence
                personOptionalDetails.personalIdentification = dg13.personalIdentification
                personOptionalDetails.dateOfIssue = dg13.dateOfIssue
                personOptionalDetails.dateOfExpiry = dg13.dateOfExpiry
                personOptionalDetails.fatherName = dg13.fatherName
                personOptionalDetails.motherName = dg13.motherName
                personOptionalDetails.oldEidNumber = dg13.oldEidNumber
                personOptionalDetails.unkIdNumber = dg13.unkIdNumber
                eid.personOptionalDetails = personOptionalDetails
            }
            eidCallback.onEidRead(eid)
        } catch (e: Exception) {
            eidCallback.onEidReadError(e)
        } finally {
            try {
                passportService?.close()
            } catch (ex: Exception) {
                eidCallback.onEidReadError(ex)
            } finally {
                eidCallback.onEidReadFinish()
            }
        }

    }
}