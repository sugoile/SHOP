package com.xsg.shop.service.Imp;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.xsg.shop.dto.ClassifyCascader;
import com.xsg.shop.dto.ClassifyTree;
import com.xsg.shop.dto.PmsProductClassifyParam;
import com.xsg.shop.mbg.mapper.PmsProductClassifyMapper;
import com.xsg.shop.mbg.model.PmsProductClassify;
import com.xsg.shop.mbg.model.PmsProductClassifyExample;
import com.xsg.shop.service.PmsProductClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理实现类
 * Created by xsg on 2020/4/7 9:54
 */
@Service
public class PmsProductClassifyServiceImp implements PmsProductClassifyService {
    @Autowired
    private PmsProductClassifyMapper pmsProductClassifyMapper;

    @Override
    public int create(PmsProductClassifyParam pmsProductClassifyParam) {
        PmsProductClassify pmsProductClassify = new PmsProductClassify();
        BeanUtil.copyProperties(pmsProductClassifyParam, pmsProductClassify);
        pmsProductClassify.setProductCount(0);
        //设置level
        SetProductCategoryLevel(pmsProductClassify);
        PmsProductClassifyExample pmsProductClassifyExample = new PmsProductClassifyExample();
        pmsProductClassifyExample.createCriteria().andNameEqualTo(pmsProductClassifyParam.getName());
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyMapper.selectByExample(pmsProductClassifyExample);
        if (pmsProductClassifies != null && pmsProductClassifies.size() > 0) {
            return -1;
        }
        return pmsProductClassifyMapper.insertSelective(pmsProductClassify);
    }

    @Override
    public List<PmsProductClassify> getOnelevelList(Integer PageNum, Integer PageSize, Long id) {
        PageHelper.startPage(PageNum, PageSize);
        PmsProductClassifyExample pmsProductClassifyExample = new PmsProductClassifyExample();
        if (id == null) {
            pmsProductClassifyExample.createCriteria().andParentidEqualTo(id);
        } else {
            pmsProductClassifyExample.createCriteria().andParentidEqualTo(id);
        }
        return pmsProductClassifyMapper.selectByExample(pmsProductClassifyExample);
    }

    @Override
    public ClassifyTree GetTreebyClassifyId(Long ID) {
        ClassifyTree tree = new ClassifyTree();
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyMapper.selectByExample(new PmsProductClassifyExample());
        List<ClassifyTree> trees = new ArrayList<>();
        pmsProductClassifies.forEach(item -> {
            if (item.getId() == ID) {
                BeanUtil.copyProperties(item, tree);
            }
            ClassifyTree t = new ClassifyTree();
            BeanUtil.copyProperties(item, t);
            trees.add(t);
        });
        for (ClassifyTree classifyTree : trees) {
            if (classifyTree.getParentid() == tree.getId()) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList<>());
                }
                tree.getChildren().add(classifyTree);
            }

            for (ClassifyTree it : trees) {
                if (it.getParentid() == classifyTree.getId()) {
                    if (classifyTree.getChildren() == null) {
                        classifyTree.setChildren(new ArrayList<>());
                    }
                    classifyTree.getChildren().add(it);
                }
            }
        }
        return tree;
    }

    @Override
    public int DeleteClassify(Long ID) {
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyMapper.selectByExample(new PmsProductClassifyExample());
        List<Long> DeleteList = new ArrayList<>();
        DeleteList.add(ID);
        findChildrenByClassifyid(ID, pmsProductClassifies, DeleteList);
        if (DeleteList == null || DeleteList.size() == 0) {
            return -1;
        } else {
            PmsProductClassifyExample pmsProductClassifyExample = new PmsProductClassifyExample();
            pmsProductClassifyExample.createCriteria().andIdIn(DeleteList);
            return pmsProductClassifyMapper.deleteByExample(pmsProductClassifyExample);
        }

    }

    @Override
    public int updateNavstatu(Long ID, Integer navStatu) {
        PmsProductClassify pmsProductClassify = new PmsProductClassify();
        pmsProductClassify.setId(ID);
        pmsProductClassify.setNavStatu(navStatu);
        return pmsProductClassifyMapper.updateByPrimaryKeySelective(pmsProductClassify);
    }

    @Override
    public int updateShowstatu(Long ID, Integer showStatu) {
        PmsProductClassify pmsProductClassify = new PmsProductClassify();
        pmsProductClassify.setId(ID);
        pmsProductClassify.setShowStatu(showStatu);
        return pmsProductClassifyMapper.updateByPrimaryKeySelective(pmsProductClassify);
    }

    @Override
    public PmsProductClassify GetClassifyById(Long ID) {
        return pmsProductClassifyMapper.selectByPrimaryKey(ID);
    }

    @Override
    public List<ClassifyTree> GetTree() {
        List<ClassifyTree> parentTree = new ArrayList<>();
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyMapper.selectByExample(new PmsProductClassifyExample());
        List<ClassifyTree> trees = new ArrayList<>();
        pmsProductClassifies.forEach(item -> {
            ClassifyTree t = new ClassifyTree();
            BeanUtil.copyProperties(item, t);
            trees.add(t);
        });

        return parentTree;
    }

    @Override
    public List<ClassifyCascader> ClassifyCascaderTree() {
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyMapper.selectByExample(new PmsProductClassifyExample());
        List<ClassifyCascader> ListclassifyCascaders = new ArrayList<>();
        List<ClassifyCascader> TreeclassifyCascaders = new ArrayList<>();
        pmsProductClassifies.forEach(pmsProductClassify -> {
            ClassifyCascader classifyCascader = new ClassifyCascader(pmsProductClassify.getName(), pmsProductClassify.getId(), pmsProductClassify.getParentid(), null);
            ListclassifyCascaders.add(classifyCascader);
        });
        for (ClassifyCascader classify : ListclassifyCascaders) {
            if (classify.getPid() == 0) {
                TreeclassifyCascaders.add(classify);
            }
            for (ClassifyCascader it : ListclassifyCascaders) {
                if (classify.getValue() == it.getPid()) {
                    if (classify.getChildren() == null) {
                        classify.setChildren(new ArrayList<>());
                    }
                    classify.getChildren().add(it);
                }
            }
        }
        return TreeclassifyCascaders;
    }

    @Override
    public List<PmsProductClassify> getThreelevelList() {
        PmsProductClassifyExample pmsProductClassifyExample = new PmsProductClassifyExample();
        pmsProductClassifyExample.createCriteria().andLevelEqualTo(2);
        List<PmsProductClassify> pmsProductClassifies = pmsProductClassifyMapper.selectByExample(pmsProductClassifyExample);
        pmsProductClassifies.forEach(pmsProductClassify -> {
            //返回各分类名(如: 女装/夹克/牛仔)
            PmsProductClassify classify = pmsProductClassifyMapper.selectByPrimaryKey(pmsProductClassify.getParentid());
            String classifyname = classify.getName() + "/";
            classify = pmsProductClassifyMapper.selectByPrimaryKey(classify.getParentid());
            StringBuilder sb = new StringBuilder(classifyname);
            sb.insert(0, classify.getName() + "/");
            classifyname = sb.toString();
            classifyname += pmsProductClassify.getName();
            pmsProductClassify.setName(classifyname);
        });
        return pmsProductClassifies;
    }

    @Override
    public int update(Long Classifyid, PmsProductClassifyParam pmsProductClassifyParam) {
        PmsProductClassify pmsProductClassify = new PmsProductClassify();
        BeanUtil.copyProperties(pmsProductClassifyParam, pmsProductClassify);
        //如果当前Classifyid为更新的pmsProductClassifyParam的id报错误
        if (Classifyid == pmsProductClassify.getParentid()) {
            return 2;
        }
        pmsProductClassify.setId(Classifyid);
        pmsProductClassify.setProductCount(0);
        //设置level
        SetProductCategoryLevel(pmsProductClassify);
        return pmsProductClassifyMapper.updateByPrimaryKeySelective(pmsProductClassify);
    }

    @Override
    public List<Long> GetParentById(Long ID) {
        PmsProductClassify pmsProductClassify = pmsProductClassifyMapper.selectByPrimaryKey(ID);
        int level = pmsProductClassify.getLevel();
        List<Long> parentids = new ArrayList<>();
        if (level != 0) {
            for (int i = 0; i < level; i++) {
                pmsProductClassify = pmsProductClassifyMapper.selectByPrimaryKey(pmsProductClassify.getParentid());
                parentids.add(pmsProductClassify.getId());
            }
        } else {
            parentids.add(pmsProductClassify.getParentid());
        }
        return parentids;
    }

    /*根据一个id找出所有包含的子id返回*/
    public List<Long> findChildrenByClassifyid(Long ID, List<PmsProductClassify> pmsProductClassifies, List<Long> returnper) {
        for (PmsProductClassify pmsProductClassify : pmsProductClassifies) {
            if (pmsProductClassify.getParentid() == ID) {
                returnper.add(pmsProductClassify.getId());
                findChildrenByClassifyid(pmsProductClassify.getId(), pmsProductClassifies, returnper);
            }
        }
        return returnper;
    }

    /**
     * 根据parentid来设置level
     */
    public void SetProductCategoryLevel(PmsProductClassify pmsProductClassify) {
        //当父分类为0时，即一级则此时设置分类级别为0即一级
        //没有父分类
        if (pmsProductClassify.getParentid() == 0) {
            pmsProductClassify.setLevel(0);
        } else {
            //有父分类的情况根据父分类来判断
            PmsProductClassify parentCategory = pmsProductClassifyMapper.selectByPrimaryKey(pmsProductClassify.getParentid());
            if (parentCategory != null) {
                pmsProductClassify.setLevel(parentCategory.getLevel() + 1);
            }

            //基本不会出现这种情况，一般只会出现在设置错误的父类情况，搜索不到该父类，直接设置为0级来修复
            else {
                pmsProductClassify.setLevel(0);
                pmsProductClassify.setParentid((long) 0);
            }
        }

    }
}
