package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.media.MediaPlayer
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.orgipdec.checkinqrcodepolodigitalmanaus.data.Constants
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.DateTime
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.SharedPreferences
import kotlinx.android.synthetic.main.qrcode_active.*
import kotlinx.android.synthetic.main.qrcode_leitura.*


class ViewQRCodeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)


        return inflater.inflate(R.layout.qrcode_active, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtQRCODE.setText(getArguments()!!.getString("qrcode")!!);
        txtData.setText(getArguments()!!.getString("data")!!);
       // SharedPreferences.getOganizador(requireActivity())

    }
}