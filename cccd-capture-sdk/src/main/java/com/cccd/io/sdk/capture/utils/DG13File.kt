package com.cccd.io.sdk.capture.utils

import android.util.Log
import net.sf.scuba.tlv.TLVInputStream
import org.jmrtd.lds.DataGroup
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.Arrays
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.text.Charsets.UTF_8


class DG13File(inputStream: InputStream?) :
    DataGroup(EF_DG13_TAG, inputStream) {
    /**
     * Returns the EID number of the holder
     *
     * @return EID number
     */
    var eidNumber: String? = null
        private set

    /**
     * Returns full name of the holder (primary and secondary identifiers).
     *
     * @return the name of the holder
     */
    var fullName: String? = null
        private set

    /**
     * Returns the date of birth of the holder
     *
     * @return the date of birth of the holder
     */
    var dateOfBirth: String? = null
        private set

    /**
     * Returns the gender of the holder
     *
     * @return the gender of the holder
     */
    var gender: String? = null
        private set

    /**
     * Returns nationality of the holder
     *
     * @return nationality of the holder
     */
    var nationality: String? = null
        private set

    /**
     * Returns the UNK Info
     *
     * @return the UNK Info
     */
    fun getUnkInfo(): List<String> {
        return unkInfo
    }

    /**
     * Returns the ethnicity of the holder
     *
     * @return the ethnicity of the holder
     */
    var ethnicity: String? = null
        private set

    /**
     * Returns the religion of the holder
     *
     * @return the religion of the holder
     */
    var religion: String? = null
        private set

    /**
     * Returns place of origin of the holder
     *
     * @return place of origin of the holder
     */
    var placeOfOrigin: String? = null
        private set

    /**
     * Returns place of residence of the holder
     *
     * @return place of residence of the holder
     */
    var placeOfResidence: String? = null
        private set

    /**
     * Returns personal identification of the holder
     *
     * @return personal identification of the holder
     */
    var personalIdentification: String? = null
        private set

    /**
     * Returns date of issue of the card
     *
     * @return date of issue of the card
     */
    var dateOfIssue: String? = null
        private set

    /**
     * Returns date of expiry of the card
     *
     * @return date of expiry of the card
     */
    var dateOfExpiry: String? = null
        private set

    /**
     * Returns the father name of the holder
     *
     * @return the father name of the holder
     */
    var fatherName: String? = null
        private set

    /**
     * Returns the mother name of the holder
     *
     * @return the mother name of the holder
     */
    var motherName: String? = null
        private set

    /**
     * Returns the old EID Number of the holder
     *
     * @return the old EID Number of the holder
     */
    var oldEidNumber: String? = null
        private set

    /**
     * Returns the UNK ID Number of the holder
     *
     * @return the UNK ID Number of the holder
     */
    var unkIdNumber: String? = null
        private set
    private val unkInfo: MutableList<String> = ArrayList()

    /**
     * data segment always start with 48, _, 2, 1, {segmentIdx}
     * 1 <= segmentIdx <= 14
     * see PREFIX_ for more info
     *
     * @param inputStream the input stream to read from
     */
    override fun readContent(inputStream: InputStream) {
        try {
            val bufferedReader = BufferedReader(
                InputStreamReader(
                    DataInputStream(
                        inputStream as? TLVInputStream ?: TLVInputStream(inputStream)
                    ), UTF_8
                )
            )
            val buf = CharArray(2048)
            val numRead = bufferedReader.read(buf)
            LOGGER.log(Level.INFO, "numRead", numRead)
            val separatorPositions: MutableList<Int> = ArrayList()
            var segmentIdx = 1
            for (i in 0 until buf.size - 5) {
                val c5 = charArrayOf(buf[i], buf[i + 1], buf[i + 2], buf[i + 3], buf[i + 4])
                if (c5[0].code == 48 && c5[2].code == 2 && c5[3].code == 1 && c5[4].code == segmentIdx) {
                    ++segmentIdx // increment next segment
                    separatorPositions.add(i)
                } else if (c5[0].code == 0 && c5[1].code == 0 && c5[2].code == 0 && c5[3].code == 0) {
                    separatorPositions.add(i) // end of data
                    break
                }
            }
            for (i in 0 until separatorPositions.size - 1) {
                val start = separatorPositions[i]
                val end = separatorPositions[i + 1]
                val subset = Arrays.copyOfRange(buf, start, end)
                // Potential empty group here
                if (subset.size < 5) {
                    continue
                }
                when (subset[4]) {
                    IDX_EID -> eidNumber =
                        String(Arrays.copyOfRange(subset, PREFIX_EID.size, subset.size))

                    IDX_FULLNAME -> fullName = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_FULLNAME.size,
                            subset.size
                        )
                    )

                    IDX_DOB -> dateOfBirth =
                        String(Arrays.copyOfRange(subset, PREFIX_DOB.size, subset.size))

                    IDX_GENDER -> gender = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_GENDER.size,
                            subset.size
                        )
                    )

                    IDX_NATIONALITY -> nationality = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_NATIONALITY.size,
                            subset.size
                        )
                    )

                    IDX_ETHNICITY -> ethnicity = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_ETHNICITY.size,
                            subset.size
                        )
                    )

                    IDX_RELIGION -> religion = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_RELIGION.size,
                            subset.size
                        )
                    )

                    IDX_POG -> placeOfOrigin =
                        String(Arrays.copyOfRange(subset, PREFIX_POG.size, subset.size))

                    IDX_POR -> placeOfResidence =
                        String(Arrays.copyOfRange(subset, PREFIX_POR.size, subset.size))

                    IDX_PERSONAL_IDENTIFICATION -> personalIdentification = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_PERSONAL_IDENTIFICATION.size,
                            subset.size
                        )
                    )

                    IDX_DATEOFISSUE -> dateOfIssue = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_DATEOFISSUE.size,
                            subset.size
                        )
                    )

                    IDX_DATEOFEXPIRY -> dateOfExpiry = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_DATEOFEXPIRY.size,
                            subset.size
                        )
                    )

                    IDX_FAMILY -> {
                        val seps: MutableList<Int> = ArrayList()
                        var j = PREFIX_FAMILY.size
                        while (j < subset.size - 2) {
                            if (subset[j].code == 48 && subset[j + 2].code == 12) {
                                seps.add(j)
                            }
                            ++j
                        }
                        if (seps.size != 2) {
                            Log.e("FAMILY", "Bad format")
                            break
                        }
                        fatherName = String(
                            Arrays.copyOfRange(
                                subset, seps[0] + PREFIX_FATHERNAME.size,
                                seps[1]
                            )
                        )
                        motherName = String(
                            Arrays.copyOfRange(
                                subset,
                                seps[1] + PREFIX_MOTHERNAME.size,
                                subset.size
                            )
                        )
                    }

                    IDX_CARDINFO -> {}
                    IDX_OLDEID -> oldEidNumber = String(
                        Arrays.copyOfRange(
                            subset,
                            PREFIX_OLDEID.size,
                            subset.size
                        )
                    )

                    IDX_CARDUNK -> unkIdNumber =
                        String(Arrays.copyOfRange(subset, PREFIX_UNK.size, subset.size))

                    else -> unkInfo.add(String(subset))
                }
            }
        } catch (e: Exception) {
            LOGGER.log(Level.WARNING, "Exception", e)
        }
    }

    @Throws(IOException::class)
    override fun writeContent(outputStream: OutputStream) {
        // NO IMPLEMENTATION
    }

    companion object {
        private val LOGGER = Logger.getLogger("org.jmrtd")
        private const val IDX_EID = 1.toChar()
        val PREFIX_EID = charArrayOf(
            48.toChar(),
            17.toChar(),
            2.toChar(),
            1.toChar(),
            1.toChar(),
            19.toChar(),
            12.toChar()
        )
        const val IDX_FULLNAME = 2.toChar()
        val PREFIX_FULLNAME = charArrayOf(
            48.toChar(),
            28.toChar(),
            2.toChar(),
            1.toChar(),
            2.toChar(),
            12.toChar(),
            23.toChar()
        )
        const val IDX_DOB = 3.toChar()
        val PREFIX_DOB = charArrayOf(
            48.toChar(),
            15.toChar(),
            2.toChar(),
            1.toChar(),
            3.toChar(),
            19.toChar(),
            10.toChar()
        )
        const val IDX_GENDER = 4.toChar()
        val PREFIX_GENDER = charArrayOf(
            48.toChar(),
            8.toChar(),
            2.toChar(),
            1.toChar(),
            4.toChar(),
            12.toChar(),
            3.toChar()
        )
        const val IDX_NATIONALITY = 5.toChar()
        val PREFIX_NATIONALITY = charArrayOf(
            48.toChar(),
            15.toChar(),
            2.toChar(),
            1.toChar(),
            5.toChar(),
            12.toChar(),
            10.toChar()
        )
        const val IDX_ETHNICITY = 6.toChar()
        val PREFIX_ETHNICITY = charArrayOf(
            48.toChar(),
            9.toChar(),
            2.toChar(),
            1.toChar(),
            6.toChar(),
            12.toChar(),
            4.toChar()
        )
        const val IDX_RELIGION = 7.toChar()
        val PREFIX_RELIGION = charArrayOf(
            48.toChar(),
            11.toChar(),
            2.toChar(),
            1.toChar(),
            7.toChar(),
            12.toChar(),
            6.toChar()
        )
        const val IDX_POG = 8.toChar()
        val PREFIX_POG = charArrayOf(
            48.toChar(),
            38.toChar(),
            2.toChar(),
            1.toChar(),
            8.toChar(),
            12.toChar(),
            33.toChar()
        )
        const val IDX_POR = 9.toChar()
        val PREFIX_POR = charArrayOf(
            48.toChar(),
            61.toChar(),
            2.toChar(),
            1.toChar(),
            9.toChar(),
            12.toChar(),
            56.toChar()
        )
        const val IDX_PERSONAL_IDENTIFICATION = 10.toChar()
        val PREFIX_PERSONAL_IDENTIFICATION = charArrayOf(
            48.toChar(),
            40.toChar(),
            2.toChar(),
            1.toChar(),
            10.toChar(),
            12.toChar(),
            35.toChar()
        )
        const val IDX_DATEOFISSUE = 11.toChar()
        val PREFIX_DATEOFISSUE = charArrayOf(
            48.toChar(),
            15.toChar(),
            2.toChar(),
            1.toChar(),
            11.toChar(),
            19.toChar(),
            10.toChar()
        )
        const val IDX_DATEOFEXPIRY = 12.toChar()
        val PREFIX_DATEOFEXPIRY = charArrayOf(
            48.toChar(),
            15.toChar(),
            2.toChar(),
            1.toChar(),
            12.toChar(),
            12.toChar(),
            10.toChar()
        )
        const val IDX_FAMILY = 13.toChar()
        val PREFIX_FAMILY =
            charArrayOf(48.toChar(), 54.toChar(), 2.toChar(), 1.toChar(), 13.toChar())
        val PREFIX_FATHERNAME = charArrayOf(48.toChar(), 25.toChar(), 12.toChar(), 23.toChar())
        val PREFIX_MOTHERNAME = charArrayOf(48.toChar(), 22.toChar(), 12.toChar(), 20.toChar())
        const val IDX_CARDINFO = 14.toChar()
        val PREFIX_CARDINFO =
            charArrayOf(48.toChar(), 3.toChar(), 2.toChar(), 1.toChar(), 14.toChar())
        const val IDX_OLDEID = 15.toChar()
        val PREFIX_OLDEID = charArrayOf(
            48.toChar(),
            14.toChar(),
            2.toChar(),
            1.toChar(),
            15.toChar(),
            19.toChar(),
            9.toChar()
        )
        const val IDX_CARDUNK = 16.toChar()
        val PREFIX_UNK = charArrayOf(
            48.toChar(),
            21.toChar(),
            2.toChar(),
            1.toChar(),
            16.toChar(),
            19.toChar(),
            16.toChar()
        )
    }
}
