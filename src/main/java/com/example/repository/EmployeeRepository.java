package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ORDER BY hire_date";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 従業員を名前で曖昧検索して取得します.
	 *
	 * @param name 従業員名
	 * @return 該当する従業員一覧 該当する従業員が存在しない場合はサイズ0件の従業員一覧を返しま
	 */
	public List<Employee> findByNameLike(String name){
		String sql = """
				SELECT 
					id,
					name,
					image,
					gender,
					hire_date,
					mail_address,
					zip_code,
					address,
					telephone,
					salary,
					characteristics,
					dependents_count 
				FROM employees 
				WHERE 
					name LIKE :name
				ORDER BY hire_date				
				""";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%"+name+"%");
		return  template.query(sql, param, EMPLOYEE_ROW_MAPPER);

	}

	/**
	 * 10件ずつの検索結果を返す.
	 *
	 * @param name 検索する名前
	 * @param limit リミット
	 * @param offset オフセット
	 * @return 検索結果の一覧(limit, offset が null の場合は検索した全件を取得)
	 */
	public List<Employee> findByNameLikeWithPagination(String name, Integer limit, Integer offset) {
		StringBuilder sql = new StringBuilder();
		sql.append("""
                SELECT 
                    id,
                    name,
                    image,
                    gender,
                    hire_date,
                    mail_address,
                    zip_code,
                    address,
                    telephone,
                    salary,
                    characteristics,
                    dependents_count 
                FROM employees 
                """);

		MapSqlParameterSource params = new MapSqlParameterSource();

		if (name != null && !name.isEmpty()) {
			sql.append("WHERE name LIKE :name ");
			params.addValue("name", "%" + name + "%");
		}

		sql.append("ORDER BY hire_date ");

		if (limit != null && offset != null) {
			sql.append("LIMIT :limit OFFSET :offset");
			params.addValue("limit", limit);
			params.addValue("offset", offset);
		}

		return template.query(sql.toString(), params, EMPLOYEE_ROW_MAPPER);
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception org.springframework.dao.DataAccessException 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}
}
