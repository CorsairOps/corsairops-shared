package com.corsairops.shared.client;

import com.corsairops.shared.dto.asset.AssetLocationRequest;
import com.corsairops.shared.dto.asset.AssetLocationResponse;
import com.corsairops.shared.dto.asset.AssetRequest;
import com.corsairops.shared.dto.asset.AssetResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.UUID;

public interface AssetServiceClient {

    @PostExchange("/api/assets")
    AssetResponse createAsset(@RequestBody @Valid AssetRequest assetRequest);

    @GetExchange("/api/assets")
    List<AssetResponse> getAllAssets();

    @GetExchange("/api/assets/count")
    Long getAssetCount();

    @GetExchange("/api/assets/{id}")
    AssetResponse getAssetById(@PathVariable("id") UUID id);

    @GetExchange("/api/assets/ids")
    List<AssetResponse> getAssetsByIds(@RequestParam("ids") String ids);

    @GetExchange("/api/assets/{id}/locations")
    List<AssetLocationResponse> getAssetLocations(@PathVariable("id") UUID id, @RequestParam(value = "max", required = false, defaultValue = "100") Integer max);

    @PutExchange("/api/assets/{id}/locations")
    void changeAssetLocation(@PathVariable("id") UUID id, @RequestBody @Valid AssetLocationRequest locationRequest);

    @PutExchange("/api/assets/{id}")
    AssetResponse updateAsset(@PathVariable("id") UUID id, @RequestBody @Valid AssetRequest assetRequest);

    @DeleteExchange("/api/assets/{id}")
    void deleteAsset(@PathVariable("id") UUID id);

}