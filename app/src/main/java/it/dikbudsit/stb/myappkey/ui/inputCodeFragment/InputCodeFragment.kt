package it.dikbudsit.stb.myappkey.ui.inputCodeFragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.dikbudsit.stb.myappkey.MainActivity

import it.dikbudsit.stb.myappkey.R
import it.dikbudsit.stb.myappkey.ViewModel.NoteViewModel
import it.dikbudsit.stb.myappkey.databinding.HomeFragmentBinding
import it.dikbudsit.stb.myappkey.databinding.InputCodeFragmentBinding
import it.dikbudsit.stb.myappkey.room.Note
import it.dikbudsit.stb.myappkey.ui.home.HomeFragment
import java.util.*

class InputCodeFragment : Fragment() {

    private lateinit var binding : InputCodeFragmentBinding
    private var noteId: String? = null
    private lateinit var note : Note


    companion object {
        fun newInstance() = InputCodeFragment()
    }

    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.input_code_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        (activity as MainActivity).setTitle("${arguments?.get("TAG")}")

        if (arguments?.getString("TAG").toString().equals("Update")){
            note = arguments?.getParcelable("note")!!

            binding.namaApp.setText(note?.nmApp)
            binding.key.setText(note?.key)
            binding.pwd.setText(note?.pwd)
        }

        binding.save.setOnClickListener{
            when(arguments?.get("TAG")){
                "Update" ->{
                    val note = Note(note.id ,binding.namaApp.text.toString(),binding.key.text.toString(), binding.pwd.text.toString())
                    viewModel.updateNote(note).run {
                        clearTask()
                        findNavController().navigateUp()
                    }
                }
                "newData" ->{
                    noteId = UUID.randomUUID().toString()
                    val note = Note(noteId!!,binding.namaApp.text.toString(),binding.key.text.toString(), binding.pwd.text.toString())
                    viewModel.insert(note).run {
                        clearTask()
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    fun clearTask(){
        binding.namaApp.text.clear()
        binding.key.text.clear()
        binding.pwd.text.clear()
    }


}
