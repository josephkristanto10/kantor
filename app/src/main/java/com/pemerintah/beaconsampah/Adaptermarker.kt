import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pemerintah.beaconsampah.R
import com.pemerintah.beaconsampah.datamarker
import kotlinx.android.synthetic.main.cardviewlistmarker.view.*


class Adaptermarker (var context : Context, var arr: ArrayList<datamarker>) : RecyclerView.Adapter<Adaptermarker.Holder>(){
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.textViewNamaKoordinatMarker.setText(arr[position].namamarker)
        holder.view.textViewKoordinatMarker.setText("("+ arr[position].latmarker + ", "+ arr[position].longmarker + ")")
        holder.view.cardviewlistmarker.setOnClickListener({

        })
    }

    override fun getItemCount(): Int = arr.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.cardviewlistmarker,parent,false))
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}