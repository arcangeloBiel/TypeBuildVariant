package com.agencytravelboat.buildvariant.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.agencytravelboat.buildvariant.R
import com.agencytravelboat.buildvariant.databinding.ItemBinding
import com.agencytravelboat.buildvariant.ui.model.ItemModel
import splitties.systemservices.layoutInflater


class ListaItensAdapter(
    private val lista: List<ItemModel>?
) : RecyclerView.Adapter<ListaItensAdapter.ViewHolder>() {

    private var clickListener: ((ItemModel) -> Unit)? = null

    fun setClickListener(onClick: (ItemModel) -> Unit) {
        clickListener = onClick
    }

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val inventarioSelecionado = lista?.get(layoutPosition)

            clickListener?.let {
                if (inventarioSelecionado != null) {
                    it(inventarioSelecionado)
                }
            }
            setClickListener { }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lista?.size ?: 0
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.containerBadges.removeAllViews()
        if (!lista.isNullOrEmpty()) {
            val itemSelecionado = lista[position]
            val descricao = "${itemSelecionado.descricao} (${itemSelecionado.codigoConferencia})"

            holder.binding.txtCodigoInventarioAlocado.text = itemSelecionado.codigoInventario.toString()
            holder.binding.txtDescicaoInventarioAlocado.text = descricao

            /// percorre a lista de badges e adiciona elas num linearlayout horizontal
            itemSelecionado.listaBadges.forEach { badge ->
                val inflater = holder.binding.containerBadges.layoutInflater
                val badgeView: Button = inflater.inflate(R.layout.badge, null) as Button

                badgeView.setBackgroundColor(Color.parseColor(badge.corBadge))
                badgeView.setTextColor(Color.parseColor(badge.corTextoBadge))
                badgeView.setBackgroundResource(R.color.teal_200)

                badgeView.text = badge.descricaoBadge

                /// O icone de relógio está no estilo padrão da badge e é removido se
                /// a descrição da badge não contiver números ou a palaver hoje.
                /// não consegui adicionar dinamicamente, só remover.
                if (!(badge.descricaoBadge.contains(Regex("\\d")) || badge.descricaoBadge.lowercase()
                        .contains("atrasado"))
                ) {
                    badgeView.setCompoundDrawables(null, null, null, null)
                }
                holder.binding.containerBadges.addView(badgeView)
            }
        }
    }
}