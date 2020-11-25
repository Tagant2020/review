package com.epitech.cashmanager.ui.scan.scannedProduct

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.epitech.cashmanager.R
import com.epitech.cashmanager.listeners.FragmentStateCallback


private const val SCANNED_PRODUCT_STRING = ""

class ScannedProductFragment : Fragment() {

    private var scannedProductString: String? = null
    private lateinit var fragmentStateCallback: FragmentStateCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            scannedProductString = it.getString(SCANNED_PRODUCT_STRING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_scanned_product, container, false)
        val closeButton: ImageButton? = root.findViewById(R.id.scanned_product_close_button)

        closeButton?.setOnClickListener {
            val transaction = requireFragmentManager().beginTransaction()

            transaction.remove(this)
            transaction.commit()
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentStateCallback.childFragmentAttached()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentStateCallback.childFragmentDetached()
    }

    fun setFragmentStateCallback(pFragmentListener: FragmentStateCallback) {
        fragmentStateCallback = pFragmentListener
    }

    companion object {
        @JvmStatic
        fun newInstance(scannedProductString: String) =
            ScannedProductFragment().apply {
                arguments = Bundle().apply {
                    putString(SCANNED_PRODUCT_STRING, scannedProductString)
                }
            }
    }
}
