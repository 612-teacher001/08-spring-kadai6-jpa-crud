package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	/**
	 * カテゴリ一覧画面表示
	 * @param model 遷移先画面にデータを引き継ぐためのスコープ
	 * @return カテゴリ一覧画面のThymeleafテンプレート名
	 */
	@GetMapping("/categories")
	public String index(Model model) {
		// カテゴリリストをcategoriesテーブルから取得
		List<Category> list = categoryRepository.findAll();
		// 取得したカテゴリリストを遷移先画面にデータを引き継ぐためにスコープに登録
		model.addAttribute("categories", list);
		// 画面遷移
		return "categories/list";
	}
}
