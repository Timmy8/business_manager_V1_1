package com.github.Timmy8.restClient.interfaces;

import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProposalsRestClient {
    List<Proposal> findAllProposals();
    Optional<Proposal> findProposal(int proposalId);
    Proposal createProposal(String name, String description, BigDecimal price);
    void updateProposal(int proposalId, String name, String description, BigDecimal price);
    void deleteProposal(int proposalId);
}
