package com.shyoon.wms.outbound.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "packaging_material")
@Comment("포장재")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PackagingMaterial {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_material_no")
    @Comment("포장재 번호")
    private Long packagingMaterialNo;

    @Column(name = "name", nullable = false)
    @Comment("포장재 이름")
    private String name;

    @Column(name = "code", nullable = false)
    @Comment("포장재 코드")
    private String code;

    @Embedded
    @Comment("포장재 치수")
    private PackagingMaterialDimension packagingMaterialDimension;

    @Column(name = "weight", nullable = false)
    @Comment("무게")
    private Long weightInGrams;

    @Column(name = "max_weight", nullable = false)
    @Comment("최대 무게")
    private Long maxWeightInGrams;

    @Column(name = "material_type", nullable = false)
    @Comment("포장재 종류")
    private MaterialType materialType;

    public PackagingMaterial(
            final String name,
            final String code,
            final PackagingMaterialDimension packagingMaterialDimension,
            final Long weightInGrams,
            final Long maxWeightInGrams,
            final MaterialType materialType) {
        validateConstructor(name, code, packagingMaterialDimension, weightInGrams, maxWeightInGrams, materialType);

        this.name = name;
        this.code = code;
        this.packagingMaterialDimension = packagingMaterialDimension;
        this.weightInGrams = weightInGrams;
        this.maxWeightInGrams = maxWeightInGrams;
        this.materialType = materialType;
    }

    private void validateConstructor(
            final String name,
            final String code,
            final PackagingMaterialDimension packagingMaterialDimension,
            final Long weightInGrams,
            final Long maxWeightInGrams,
            final MaterialType materialType) {
        Assert.hasText(name, "포장재 이름은 필수입니다.");
        Assert.hasText(code, "포장재 코드는 필수입니다.");
        Assert.notNull(packagingMaterialDimension, "포장재 치수는 필수입니다.");
        Assert.notNull(weightInGrams, "무게는 필수입니다.");
        Assert.notNull(maxWeightInGrams, "최대 무게는 필수입니다.");
        Assert.notNull(materialType, "포장재 종류는 필수입니다.");
    }

    public void assignNo(Long packagingMaterialNo) {
        this.packagingMaterialNo = packagingMaterialNo;
    }

    public boolean isAvailable(Long totalWeight, Long totalVolume) {
        return maxWeightInGrams >= totalWeight && packagingMaterialDimension.isAvailable(totalVolume);
    }

    public Long outerVolume() {
        return packagingMaterialDimension.outerVolume();
    }
}
