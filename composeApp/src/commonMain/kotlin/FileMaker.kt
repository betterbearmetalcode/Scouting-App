import nodes.matchScoutArray
import org.json.JSONArray
import org.json.JSONObject

fun getJsonFromMatchHash(): JSONObject {
    val jsonObject = JSONObject()
    matchScoutArray.keys.forEach {
        jsonObject.put(it.toString(), JSONArray())
        val map = matchScoutArray[it]
        map?.values?.forEach { outputString ->
            (jsonObject[it.toString()] as JSONArray).put(outputString)
        }
    }

    return jsonObject
}
