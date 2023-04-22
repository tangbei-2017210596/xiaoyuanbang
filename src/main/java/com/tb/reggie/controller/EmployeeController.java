package com.tb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tb.reggie.common.R;
import com.tb.reggie.entity.Employee;
import com.tb.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    private R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp==null){
            return R.error("登陆失败");
        }
        String password = employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());

//      密码比对
        if (!(password.equals(emp.getPassword()))){
            return R.error("密码不一致");
        }
        if (emp.getStatus()==0){
            return R.error("账号已经禁用");
        }
        HttpSession session = request.getSession();
        session.setAttribute("employeeid",emp.getId());

        return R.success(emp);
    }

    @PostMapping("/logout")
    private R<String> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("employeeid");
        return R.success("退出成功");
    }
    @PostMapping
    private R<String> addEmployee(@RequestBody Employee employee,HttpServletRequest request){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        boolean save = employeeService.save(employee);
        return R.success("新增员工成功");
    }
    @GetMapping("/page")
    public R<Page> pagination(Integer page,@RequestParam("pageSize") Integer pagesize, String name){
        log.info("page={},pagesize={},name={}",page,pagesize,name);
        //分页构造器
        Page pageInfo = new Page(page, pagesize);
        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

        @PutMapping
        private R<String> update(@RequestBody Employee employee,HttpServletRequest request){
            employee.setUpdateTime(LocalDateTime.now());
            employee.setUpdateUser((Long) request.getSession().getAttribute("employeeid"));
            employeeService.updateById(employee);
            return R.success("员工信息修改成功");
        }

        @GetMapping("/{id}")
        private R<Employee> getById(@PathVariable("id") Long id){
            Employee employee = employeeService.getById(id);
            if (employee!=null){
                return R.success(employee);
            }else {
                return R.error("没有查询到对应员工信息");
            }
    }
}

