package com.epitech.cashmanager.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.epitech.cashmanager.R
import com.epitech.cashmanager.listeners.FragmentStateCallback
import com.epitech.cashmanager.ui.scan.scannedProduct.ScannedProductFragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView


class ScanFragment : Fragment(), FragmentStateCallback, BarcodeCallback {

    private val REQUEST_CODE = 100

    private lateinit var scanViewModel: ScanViewModel
    private lateinit var barcodeView: CompoundBarcodeView

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        scanViewModel = ViewModelProviders.of(this).get(ScanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_scan, container, false)

        barcodeView = root.findViewById(R.id.barcode_scanner_view)
        barcodeView.setStatusText("")
        barcodeView.decodeContinuous(this);
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!checkCameraPermission()) {
            activity?.let {
                requestPermissions(it, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkCameraPermission()) {
            barcodeView.resume()
        }
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }

    private fun checkCameraPermission(): Boolean {
        return context?.let {
            ActivityCompat.checkSelfPermission(it, Manifest.permission.CAMERA)
        } != PackageManager.PERMISSION_DENIED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun barcodeResult(result: BarcodeResult) {
        if (result.text != null) {
            barcodeView.setStatusText(result.text)
            val transaction = requireFragmentManager().beginTransaction()
            val fragment = ScannedProductFragment.newInstance(result.text)

            fragment.setFragmentStateCallback(this)
            transaction.add(R.id.constraintLayout, fragment)
            transaction.commit()
        }
    }

    override fun childFragmentAttached() {
        barcodeView.pause()
    }

    override fun childFragmentDetached() {
        barcodeView.setStatusText("")
        if (checkCameraPermission()) {
            barcodeView.resume()
        }
    }
}
