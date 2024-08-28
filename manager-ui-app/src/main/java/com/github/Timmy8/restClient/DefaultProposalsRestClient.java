package com.github.Timmy8.restClient;

import com.github.Timmy8.controller.payload.NewClientPayload;
import com.github.Timmy8.controller.payload.NewProposalPayload;
import com.github.Timmy8.controller.payload.UpdateClientPayload;
import com.github.Timmy8.controller.payload.UpdateProposalPayload;
import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.restClient.exception.BadRequestException;
import com.github.Timmy8.restClient.interfaces.ProposalsRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultProposalsRestClient implements ProposalsRestClient {
    private final RestClient restClient;

    @Override
    public List<Proposal> findAllProposals() {
        return restClient
                .get()
                .uri("manager-api/proposals")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public Optional<Proposal> findProposal(int proposalId) {
        try{
            return Optional.ofNullable(restClient
                    .get()
                    .uri("manager-api/proposals/{proposalId}", proposalId)
                    .retrieve()
                    .body(Proposal.class));
        } catch (HttpClientErrorException.NotFound exception){
            return Optional.empty();
        }
    }

    @Override
    public Proposal createProposal(String name, String description, BigDecimal price) {
        try{
            return restClient
                    .post()
                    .uri("manager-api/proposals")
                    .body(new NewProposalPayload(name,description,price))
                    .retrieve()
                    .body(Proposal.class);
        } catch (HttpClientErrorException.BadRequest exception){
            List<String> errors = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});
            throw new BadRequestException(errors);
        }
    }

    @Override
    public void updateProposal(int proposalId, String name, String description, BigDecimal price) {
        try{
            restClient
                    .patch()
                    .uri("manager-api/proposals/{proposalId}", proposalId)
                    .body(new UpdateProposalPayload(name, description, price))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception){
            List<String> errors = exception.getResponseBodyAs(new ParameterizedTypeReference<>() {});
            throw new BadRequestException(errors);
        }
    }

    @Override
    public void deleteProposal(int proposalId) {
        try{
            restClient
                    .delete()
                    .uri("manager-api/proposals/{proposalId}", proposalId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception){
            throw new NoSuchElementException(exception);
        }
    }
}
