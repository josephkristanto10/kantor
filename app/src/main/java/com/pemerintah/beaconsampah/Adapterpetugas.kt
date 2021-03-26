import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pemerintah.beaconsampah.R
import com.pemerintah.beaconsampah.daftarpetugas
import com.pemerintah.beaconsampah.datamarker
import com.pemerintah.beaconsampah.datapetugas
import kotlinx.android.synthetic.main.cardviewlistmarker.view.*
import kotlinx.android.synthetic.main.cardviewlistmarker.view.cardviewlistmarker
import kotlinx.android.synthetic.main.cardviewlistpetugas.view.*


class Adapterpetugas (var context : Context, var arr: ArrayList<datapetugas>, var statushariini:Int) : RecyclerView.Adapter<Adapterpetugas.Holder>(){
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.textViewNamaPetugas.setText(arr[position].namapetugas)
//        holder.view.textViewStatusHariIni.setText()
        var status = "off"
        if(statushariini.equals(0))
        {
            status = arr[position].harisenin
        }
        if(statushariini.equals(1))
        {
            status = arr[position].hariselasa
        }
        if(statushariini.equals(2))
        {
            status = arr[position].harirabu
        }
        if(statushariini.equals(3))
        {
            status = arr[position].harikamis
        }
        if(statushariini.equals(4))
        {
            status = arr[position].harijumat
        }
        if(statushariini.equals(5))
        {
            status = arr[position].harisabtu
        }
        if(statushariini.equals(6))
        {
            status = arr[position].harisabtu
        }
        holder.view.textViewStatusHariIni.setText("Status : $status")
        holder.view.cardviewlistmarker.setOnClickListener({
            (context as daftarpetugas).customdialogpetugas(arr[position].namapetugas, arr[position].poolpetugas, arr[position].handphonepetugas, status)
        })
    }

    override fun getItemCount(): Int = arr.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.cardviewlistpetugas,parent,false))
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}