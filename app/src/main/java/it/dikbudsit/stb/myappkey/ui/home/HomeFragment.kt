package it.dikbudsit.stb.myappkey.ui.home

import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import it.dikbudsit.stb.myappkey.MainActivity
import it.dikbudsit.stb.myappkey.R
import it.dikbudsit.stb.myappkey.ViewModel.NoteViewModel
import it.dikbudsit.stb.myappkey.adapter.NoteListAdapter
import it.dikbudsit.stb.myappkey.databinding.HomeFragmentBinding
import it.dikbudsit.stb.myappkey.room.Note
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment(), NoteListAdapter.onDeleteClickListeners {

    private lateinit var binding: HomeFragmentBinding
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBiometricPrompt()
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        (activity as MainActivity).setTitle("Home Fragment")
        val adapter = activity?.let { NoteListAdapter(it, this) }

        loadData(adapter!!)

        binding.refresh.setOnRefreshListener {
            loadData(adapter!!)
        }


//        showBiometricPrompt()
        fab.setOnClickListener {
            findNavController().navigate(R.id.inputCodeFragment, bundleOf("TAG" to "newData")) }
    }

    private fun loadData(adapter: NoteListAdapter) {
        viewModel.getmAllNote()?.observe(viewLifecycleOwner, Observer {
            adapter.setNotes(it as List<Note>)
        })
        binding.recKey.setHasFixedSize(true)
        binding.recKey.adapter = adapter
        binding.recKey.layoutManager = manager()
    }

    private fun manager() : RecyclerView.LayoutManager {
        val layoutManager = object :LinearLayoutManager(activity, VERTICAL, false){
            override fun onLayoutChildren(recycler: Recycler?, state: RecyclerView.State?) {
                super.onLayoutChildren(recycler, state)
                val firstItemVisible  = findFirstVisibleItemPosition()
                if (firstItemVisible !=0){
                    if (firstItemVisible == -1)
                        isRefresing(true)
                    return
                }
                isRefresing(false)

            }
        }
        return layoutManager
    }

    private fun isRefresing(b: Boolean): Boolean {
        binding.refresh.setRefreshing(b)
        binding.recKey.setVisibility(if (b) View.GONE else View.VISIBLE)
        binding.notfond.setVisibility(if (b) View.VISIBLE else View.GONE)
        return b
    }

    private fun showBiometricPrompt() {
        val executor = (activity as MainActivity).mainExecutor
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("${activity?.resources?.getString(R.string.app_name)}  Check !!!")
            .setNegativeButton("Cancel", executor, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    activity?.finish()
                    dialog?.dismiss()
                }

            })
            .build()

        biometricPrompt.authenticate(CancellationSignal(), executor, object :
            BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Log.i(javaClass.simpleName,result.toString())
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(activity, errString.toString(), Toast.LENGTH_LONG).show()
                activity?.finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.i(javaClass.simpleName, "OnFailed")
            }
        })

    }

    override fun updateDelete(myNote: Note?, TAG: String) {

        when(TAG){
            "Update" -> {
                val bundle = Bundle()
                bundle.putParcelable("note", myNote)
                bundle.putString("TAG", "Update")
                findNavController().navigate(R.id.inputCodeFragment, bundle)
            }
            "Delete" ->
                viewModel.deleteNote(myNote).run { Toast.makeText(activity, "Berhasil delete", Toast.LENGTH_LONG).show()}
        }
    }

    override fun isVisiblePassword(mTextPwd: TextView, b: Boolean) {
        when {
            !b -> {
                mTextPwd.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }else -> {
                mTextPwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->{
                showBiometricPrompt()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
