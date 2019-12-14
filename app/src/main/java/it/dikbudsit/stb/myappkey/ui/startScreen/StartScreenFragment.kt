package it.dikbudsit.stb.myappkey.ui.startScreen

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.dikbudsit.stb.myappkey.MainActivity
import it.dikbudsit.stb.myappkey.R
import it.dikbudsit.stb.myappkey.databinding.StartScreenFragmentBinding
import java.util.concurrent.Executors

class StartScreenFragment : Fragment() {

    private lateinit var binding: StartScreenFragmentBinding

    companion object {
        fun newInstance() =
            StartScreenFragment()
    }

    private lateinit var viewModel: StartScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.start_screen_fragment, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StartScreenViewModel::class.java)
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        (activity as MainActivity).supportActionBar?.hide()
        (activity as MainActivity).isVisible(false)


    }

}
