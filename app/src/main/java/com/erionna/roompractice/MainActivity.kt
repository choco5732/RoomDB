package com.erionna.roompractice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.erionna.roompractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // db 안에 내용 show하기
        binding.btnShow.setOnClickListener {
            show()
        }

        binding.btnAdd.setOnClickListener {
            add()
        }

        binding.btnDelete.setOnClickListener {
            delete()
        }

        binding.btnUpdate.setOnClickListener {
            update()
        }
    }


    private fun add() {
        val text = "반갑수다"
        val mean = "대충 반갑다는 뜻"
        val type = "동사"
        val word = Word(text = text, mean = mean, type = type)

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.insert(word)
            runOnUiThread {
                Toast.makeText(this, "저장이 완료 되었습니다!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun show() {
        Thread {
            val data = AppDatabase.getInstance(this)?.wordDao()?.getAll()
            runOnUiThread {
                binding.tvDb.text = data.toString()
            }
        }.start()
    }

    private fun delete() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { data ->
                AppDatabase.getInstance(this)?.wordDao()?.delete(data)
            }
            runOnUiThread {
                Toast.makeText(this, "삭제됬십니더", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun update() {
        Thread {
            val lastWord =
                AppDatabase.getInstance(this)?.wordDao()?.getLatestWord() ?: return@Thread
            AppDatabase.getInstance(this)?.wordDao()
                ?.update(lastWord.copy(text = "바까주이소", mean = "바꼈다는 뜻", type = "신속|정확"))
            runOnUiThread {
                Toast.makeText(this, "업데이트 완료", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}
