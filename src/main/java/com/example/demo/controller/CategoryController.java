package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * カテゴリを新規登録する
	 * @param name 登録するカテゴリ名
	 * @return カテゴリ一覧画面のThymeleafテンプレート名
	 */
	@PostMapping("/categories/add")
	public String store(@RequestParam(defaultValue = "") String name) {
		// リクエストパラメータをもとにカテゴリクラスをインスタンス化
		Category category = new Category(name);
		// カテゴリインスタンスをcategoriesテーブルに登録
		categoryRepository.save(category);
		// 画面遷移
		return "redirect:/categories";
	}
	
	/**
	 * 新規登録画面表示
	 * @return 新規登録画面のThymeleafテンプレート名
	 */
	@GetMapping("/categories/add")
	public String add() {
		// 画面遷移
		return "categories/add";
	}
	
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
