package com.example.kiddoshine.ui.marketplace

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddoshine.R
import com.example.kiddoshine.ui.detailproduct.ProductDetailActivity

class MarketplaceFragment : Fragment() {

    private val marketplaceViewModel: MarketplaceViewModel by viewModels()
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.fragment_marketplace, container, false)

        productRecyclerView = binding.findViewById(R.id.product_recycler_view)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        productRecyclerView.layoutManager = layoutManager

        productAdapter = ProductAdapter(emptyList()) { product ->
            showProductDetail(product)
        }
        productRecyclerView.adapter = productAdapter

        val productData = listOf(
            produk(1, getString(R.string.sgm_3), getString(R.string.deskripsi_sgm_anak3), "Anak" , 56329.00 , R.drawable.sgm_anak3 , "https://www.tokopedia.com/sgmofficialstore/sgm-eksplor-3-dengan-ironc-susu-bubuk-rasa-vanilla-600gram-ald?extParam=src%3Dshop%26whid%3D2901081"),
            produk(2, getString(R.string.vidoran_bunda), getString(R.string.deskripsi_vidoran_bunda) , "Bunda" , 69540.00 , R.drawable.vidoran_bunda , "https://www.tokopedia.com/tempostore/vidoran-ibunda-vanilla-kacang-hijau-350gr-susu-ibu-hamil-2-box?extParam=src%3Dshop%26whid%3D3200204"),
            produk(4, getString(R.string.vidoran_anak_1), getString(R.string.deskripsi_vidoran_anak_1) , "Anak" , 114840.00 , R.drawable.vidoran_anak , "https://www.tokopedia.com/tempostore/vidoran-xmart-1-imun-up-susu-formula-vanilla-700-g-2-box?extParam=src%3Dshop%26whid%3D3200204"),
            produk(5, getString(R.string.vidoran_anak_3), getString(R.string.deskripsi_vidoran_anak_3) , "Anak" , 107316.00 , R.drawable.vidoran_anak3 , "https://www.tokopedia.com/tempostore/vidoran-xmart-3-imun-up-susu-formula-vanilla-700-g-2-pcs?extParam=whid%3D3200204%26src%3Dshop"),
            produk(6, getString(R.string.bebelove_1), getString(R.string.deskripsi_bebelac) , "Anak" , 77300.00 , R.drawable.bebelove1 , "https://www.tokopedia.com/bebelacofficialstore/bebelove-1-formula-bayi-bubuk-400-gr?extParam=whid%3D12903139%26src%3Dshop"),
            produk(7, getString(R.string.bebelove_2), getString(R.string.deskripsi_bebelac1) , "Anak" , 77300.00 , R.drawable.bebelove2 , "https://www.tokopedia.com/bebelacofficialstore/bebelove-2-formula-bayi-bubuk-400-gr?extParam=src%3Dshop%26whid%3D12903139"),
            produk(8, getString(R.string.bebelac_3), getString(R.string.deskripsi_bebelac3) , "Anak" , 151200.00 , R.drawable.bebelac3 , "https://www.tokopedia.com/bebelacofficialstore/bebelac-3-vanila-susu-pertumbuhan-bubuk-800-gr?extParam=src%3Dshop%26whid%3D12903139"),
            produk(9, getString(R.string.sgm_bunda), getString(R.string.deskripsi_sgm_bunda) , "Bunda" , 22525.00 , R.drawable.sgm_bunda , "https://www.tokopedia.com/sgmofficialstore/sgm-bunda-pro-gressmaxx-tinggi-zat-besi-cokelat-susu-bubuk-150gram?extParam=src%3Dshop%26whid%3D2901081"),
            produk(10, getString(R.string.sgm_1), getString(R.string.deskripsi_sgm_anak) , "Anak" , 57030.00 , R.drawable.sgm_anak , "https://www.tokopedia.com/sgmofficialstore/sgm-ananda-2-susu-formula-bayi-6-12-bln-600g?extParam=src%3Dshop%26whid%3D2901081"),
            produk(11, getString(R.string.sgm_2), getString(R.string.deskripsi_sgm_anak1) , "Anak" , 53190.00 , R.drawable.sgm_anak1 , "https://www.tokopedia.com/sgmofficialstore/sgm-eksplor-isopro-soy-1-vanilla-susu-pertumbuhan-400gr-ald?extParam=src%3Dshop%26whid%3D2901081"),
            produk(12, getString(R.string.sgm_3), getString(R.string.deskripsi_sgm_anak3), "Anak" , 56329.00 , R.drawable.sgm_anak3 , "https://www.tokopedia.com/sgmofficialstore/sgm-eksplor-3-dengan-ironc-susu-bubuk-rasa-vanilla-600gram-ald?extParam=src%3Dshop%26whid%3D2901081")
        )

        marketplaceViewModel.setProductData(productData)

        marketplaceViewModel.productList.observe(viewLifecycleOwner, Observer { productList ->
            productAdapter.updateData(productList)
        })

        return binding
    }

    private fun showProductDetail(product: produk) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra("product_name", product.name)
        intent.putExtra("product_description", product.description)
        intent.putExtra("product_price", product.price)
        intent.putExtra("product_image", product.imageResId)
        intent.putExtra("product_url", product.url)
        startActivity(intent)
    }
}