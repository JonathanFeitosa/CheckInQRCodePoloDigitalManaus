package br.orgipdec.checkinqrcodepolodigitalmanaus

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import br.orgipdec.checkinqrcodepolodigitalmanaus.utils.SharedPreferences
import kotlinx.android.synthetic.main.qrcode_active.*


class ViewQRCodeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)


        return inflater.inflate(R.layout.qrcode_active, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         txtQRCODE.setText(getArguments()!!.getString("qrcode")!!);
        // SharedPreferences.getOganizador(requireActivity())
       // SharedPreferences.getIDPalestra(requireActivity())
    }
}