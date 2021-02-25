import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

object Loader {
    fun load(filename: String): MutableList<MutableList<Double>>{
        val res = mutableListOf<MutableList<Double>>()
        try{
            val br = BufferedReader(
                InputStreamReader(
                    FileInputStream(filename), "UTF-8"
                )
            )
            while (br.readLine().apply{
                res.add(split(';').map { it.toDouble() }.toMutableList())
                } != null){}
        } catch (e: Exception){
            println("Ошибка при чтении из файла")
        }
        return res
    }
}