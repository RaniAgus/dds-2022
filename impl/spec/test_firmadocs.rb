require 'rspec'
require_relative '../src/firmadocs'

describe TrueClass do
  let(:la_verdad) do
    true
  end

  it 'la verdad' do
    expect(la_verdad).to be_truthy
  end
end
